package com.lfokazi.rs.controller.document.customer.statement;

import com.lfokazi.dto.statement.download.StatementDownloadParams;
import com.lfokazi.dto.statement.download.filter.StatementDownloadFilter;
import com.lfokazi.dto.statement.download.info.StatementDownloadInfo;
import com.lfokazi.dto.statement.filter.StatementFilter;
import com.lfokazi.rs.dto.common.RsPagedResources;
import com.lfokazi.rs.dto.statement.RsStatement;
import com.lfokazi.rs.dto.statement.download.RsStatementDownload;
import com.lfokazi.rs.mapper.document.customer.statement.RsStatementMapper;
import com.lfokazi.rs.mapper.document.customer.statement.download.RsStatementDownloadMapper;
import com.lfokazi.rs.path.RsPath;
import com.lfokazi.service.statement.download.load.StatementDownloadService;
import com.lfokazi.service.statement.load.StatementService;
import com.lfokazi.service.statement.save.download.StatementDownloader;
import com.lfokazi.service.statement.save.upload.StatementUploader;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping(RsPath.CUSTOMER_STATEMENTS)
@RequiredArgsConstructor
public class RsStatementController {
    private final StatementService statementService;
    private final StatementDownloadService statementDownloadService;
    private final StatementUploader statementUploader;
    private final StatementDownloader statementDownloader;
    private final RsStatementMapper rsStatementMapper;
    private final RsStatementDownloadMapper rsStatementDownloadMapper;

    @GetMapping
    @Transactional(readOnly = true)
    public RsPagedResources<RsStatement> statements(@PageableDefault(sort = "id", direction = DESC) Pageable pageable,
                                                    @Valid StatementFilter filter) {
        return rsStatementMapper.mapPage(statementService.findStatements(pageable, filter));
    }

    @GetMapping("/downloads")
    @Transactional(readOnly = true)
    public RsPagedResources<RsStatementDownload> downloads(@PageableDefault(sort = "id", direction = DESC)
                                                           Pageable pageable, @Valid StatementDownloadFilter filter) {
        return rsStatementDownloadMapper.mapPage(statementDownloadService.findDownloads(pageable, filter));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/upload")
    @Transactional
    public void upload(@RequestPart(value = "fileData") MultipartFile file,
                              @RequestParam @Positive long customerId) {
         statementUploader.uploadAsync(customerId, file);
    }

    @GetMapping("/{statementId}/downloads")
    @Transactional(readOnly = true)
    public RsPagedResources<RsStatementDownload> statementDownloads(@PageableDefault(sort = "id", direction = DESC)
                                                           Pageable pageable, @PathVariable long statementId,
                                                                    @Valid StatementDownloadFilter filter) {
        return rsStatementDownloadMapper.mapPage(statementDownloadService.findDownloads(pageable, statementId, filter));
    }

    @PostMapping("/{statementId}/download")
    public StatementDownloadInfo download(@PathVariable @Positive long statementId,
                                          @RequestBody StatementDownloadParams params) {
        return statementDownloader.download(statementId, params);
    }
}
