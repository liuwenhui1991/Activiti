package org.activiti.editor.language.xml;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorFactory;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.Test;

public class InCompleteSignalConverterTest extends AbstractConverterTest {

    @Test
    public void convertXMLToModel() throws Exception {
        BpmnModel bpmnModel = readXMLFile();
        validateModel(bpmnModel);
    }

    protected String getResource() {
        return "incompletesignalmodel.bpmn";
    }

    private void validateModel(BpmnModel model) {
        FlowElement flowElement = model.getMainProcess().getFlowElement("task");
        assertNotNull(flowElement);
        assertThat(flowElement).isInstanceOf(UserTask.class);
        assertEquals("task", flowElement.getId());

        ProcessValidator processValidator = new ProcessValidatorFactory().createDefaultProcessValidator();
        List<ValidationError> errors = processValidator.validate(model);
        assertNotNull(errors);
        assertEquals(2, errors.size());
    }
}
