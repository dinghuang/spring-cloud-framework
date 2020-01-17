package org.dinghuang.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.dto.PageableSearchDTO;
import org.dinghuang.core.dto.ValidList;
import org.dinghuang.core.exception.CommonException;
import org.dinghuang.demo.dto.test.TestCreateDTO;
import org.dinghuang.demo.dto.test.TestCreateOrUpdateDTO;
import org.dinghuang.demo.dto.test.TestDTO;
import org.dinghuang.demo.dto.test.TestUpdateDTO;
import org.dinghuang.demo.service.TestService;
import org.jodconverter.DocumentConverter;
import org.jodconverter.document.DefaultDocumentFormatRegistry;
import org.jodconverter.document.DocumentFormat;
import org.jodconverter.office.OfficeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 测试对象Controller
 *
 * @author dinghuang123@gmail.com
 * @since 2020/01/16
 */
@Api(value = "测试对象", tags = {"测试对象"})
@RestController
@RequestMapping(value = "/v1/tests")
public class TestController {

    private static final String DOC = "doc";
    private static final String PDF = ".pdf";
    private static final String DOCX = "docx";
    private static final String PPT = "ppt";
    private static final String PPTX = "pptx";
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";
    private static final String TXT = "txt";
    private static final String UTF_8 = "utf-8";
    private static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String FILE_NAME_HEADER = "attachment;filename=";
    private static final String FILE_TEMP_DIR = "fileTempDir";
    private static final String ROOT_PATH = "user.dir";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private TestService testService;

    @Autowired(required = false)
    private DocumentConverter documentConverter;

    @Autowired
    private RestTemplate httpClientTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @GetMapping("download_file")
    @ApiOperation(value = "生成pdf文件")
    public void toPdfFile(@ApiParam(value = "文件下载地址", required = true)
                          @RequestParam String url,
                          HttpServletResponse httpServletResponse) {
        ResponseEntity<byte[]> response = httpClientTemplate.getForEntity(url, byte[].class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String fileName = UUID.randomUUID().toString() + PDF;
            String fileType = "docx";
            String filePath = System.getProperty(ROOT_PATH) + File.separator + FILE_TEMP_DIR + File.separator + fileName;
            BufferedInputStream bufferedInputStream = null;
            OutputStream outputStream = null;
            FileInputStream fileInputStream = null;
            File file = new File(filePath);
            try (InputStream inputStream = new ByteArrayInputStream(response.getBody())) {
                //使用response,将pdf文件以流的方式发送的前段
                //文件转化
                DocumentFormat documentFormat = getDocumentFormat(fileType);
                if (documentFormat != null) {
                    documentConverter.convert(inputStream, true).as(documentFormat).to(file).execute();
                    httpServletResponse.reset();
                    httpServletResponse.setContentType(APPLICATION_OCTET_STREAM);
                    httpServletResponse.setCharacterEncoding(UTF_8);
                    httpServletResponse.setContentLength((int) file.length());
                    httpServletResponse.setHeader(CONTENT_DISPOSITION, FILE_NAME_HEADER + fileName);
                    byte[] buff = new byte[1024];
                    outputStream = httpServletResponse.getOutputStream();
                    fileInputStream = new FileInputStream(file);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    int i;
                    while ((i = bufferedInputStream.read(buff)) != -1) {
                        outputStream.write(buff, 0, i);
                        outputStream.flush();
                    }
                }
            } catch (IOException | OfficeException e) {
                LOGGER.error("转换pdf失败:{}", e);
            } finally {
                try {
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    file.deleteOnExit();
                } catch (IOException e) {
                    LOGGER.error("关闭流失败:{}", e);
                }
            }
        }
    }

    private DocumentFormat getDocumentFormat(String fileType) {
        DocumentFormat documentFormat = null;
        switch (fileType.toLowerCase()) {
            case DOC:
                documentFormat = DefaultDocumentFormatRegistry.DOC;
                break;
            case DOCX:
                documentFormat = DefaultDocumentFormatRegistry.DOCX;
                break;
            case PPT:
                documentFormat = DefaultDocumentFormatRegistry.PPT;
                break;
            case PPTX:
                documentFormat = DefaultDocumentFormatRegistry.PPTX;
                break;
            case XLS:
                documentFormat = DefaultDocumentFormatRegistry.XLS;
                break;
            case XLSX:
                documentFormat = DefaultDocumentFormatRegistry.XLSX;
                break;
            case TXT:
                documentFormat = DefaultDocumentFormatRegistry.TXT;
                break;
            default:
                break;

        }
        return documentFormat;
    }

    @PostMapping
    @ApiOperation(value = "创建测试对象")
    public ResponseEntity<TestDTO> create(@ApiParam(value = "创建测试对象", required = true)
                                          @Valid @RequestBody TestCreateDTO testCreateDTO) {
        return Optional.ofNullable(testService.create(testCreateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.Test.create"));
    }

    @PutMapping
    @ApiOperation(value = "更新测试对象")
    public ResponseEntity<TestDTO> update(@ApiParam(value = "更新测试对象", required = true)
                                          @Valid @RequestBody TestUpdateDTO testUpdateDTO) {
        return Optional.ofNullable(testService.update(testUpdateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.Test.update"));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除测试对象")
    public void deleteById(@ApiParam(value = "主键", required = true)
                           @PathVariable(name = "id") Long id) {
        testService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询测试对象")
    public ResponseEntity<TestDTO> queryById(@ApiParam(value = "id", required = true)
                                             @PathVariable(name = "id") Long id) {
        return Optional.ofNullable(testService.queryById(id))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.Test.queryById"));
    }

    @GetMapping(value = "/query_by_page")
    @ApiOperation(value = "分页查询测试对象")
    public ResponseEntity<PageableDTO<TestDTO>> queryByPage(@Valid PageableSearchDTO pageableSearchDTO) {
        return Optional.ofNullable(testService.queryByPage(pageableSearchDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.Test.queryByPage"));
    }

    @PostMapping(value = "/batch_create_or_update")
    @ApiOperation(value = "批量创建、更新测试对象")
    public ResponseEntity<List<TestDTO>> batchCreateOrUpdate(@ApiParam(value = "创建、更新测试对象列表", required = true)
                                                             @Valid @RequestBody ValidList<TestCreateOrUpdateDTO> testCreateOrUpdateDTOS) {
        return Optional.ofNullable(testService.batchCreateOrUpdate(testCreateOrUpdateDTOS))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.Test.batchCreateOrUpdate"));
    }

    @DeleteMapping(value = "/batch_deleted")
    @ApiOperation(value = "批量删除测试对象")
    public void batchDeleted(@ApiParam(value = "批量删除测试对象id列表", required = true)
                             @Valid @RequestBody List<Long> ids) {
        testService.batchDeleted(ids);
    }

}
