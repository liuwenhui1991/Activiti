package org.activiti.editor.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.junit.jupiter.api.Test;

public class BoundaryEventConverterTest extends AbstractConverterTest {

  @Test
  public void convertJsonToModel() throws Exception {
    BpmnModel bpmnModel = readJsonFile();
    validateModel(bpmnModel);
  }

  @Test
  public void doubleConversionValidation() throws Exception {
    BpmnModel bpmnModel = readJsonFile();
    bpmnModel = convertToJsonAndBack(bpmnModel);
    validateModel(bpmnModel);
  }

  @Override
  protected String getResource() {
    return "test.boundaryeventmodel.json";
  }

  private void validateModel(BpmnModel model) {

    BoundaryEvent errorElement = (BoundaryEvent) model.getMainProcess().getFlowElement("errorEvent", true);
    ErrorEventDefinition errorEvent = (ErrorEventDefinition) extractEventDefinition(errorElement);
    assertTrue(errorElement.isCancelActivity()); // always true
    assertEquals("errorRef", errorEvent.getErrorRef());
    assertEquals("sid-F21E9F4D-EA19-44DF-B1D3-14663A809CAE", errorElement.getAttachedToRefId());

    BoundaryEvent signalElement = (BoundaryEvent) model.getMainProcess().getFlowElement("signalEvent", true);
    SignalEventDefinition signalEvent = (SignalEventDefinition) extractEventDefinition(signalElement);
    assertFalse(signalElement.isCancelActivity());
    assertEquals("signalRef", signalEvent.getSignalRef());
    assertEquals("sid-F21E9F4D-EA19-44DF-B1D3-14663A809CAE", errorElement.getAttachedToRefId());

    BoundaryEvent messageElement = (BoundaryEvent) model.getMainProcess().getFlowElement("messageEvent", true);
    MessageEventDefinition messageEvent = (MessageEventDefinition) extractEventDefinition(messageElement);
    assertFalse(messageElement.isCancelActivity());
    assertEquals("messageRef", messageEvent.getMessageRef());
    assertEquals("sid-F21E9F4D-EA19-44DF-B1D3-14663A809CAE", errorElement.getAttachedToRefId());

    BoundaryEvent timerElement = (BoundaryEvent) model.getMainProcess().getFlowElement("timerEvent", true);
    TimerEventDefinition timerEvent = (TimerEventDefinition) extractEventDefinition(timerElement);
    assertFalse(timerElement.isCancelActivity());
    assertEquals("PT5M", timerEvent.getTimeDuration());
    assertEquals("sid-F21E9F4D-EA19-44DF-B1D3-14663A809CAE", errorElement.getAttachedToRefId());

  }

}
