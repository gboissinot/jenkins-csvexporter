package com.boissinot.jenkins.csvexporter.service.extractor.jenkins;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import org.springframework.integration.Message;

/**
 * @author Gregory Boissinot
 */
public interface JobExtractor {

    public OutputCSVJobObj getCVSObj(Message jobMessage);

}
