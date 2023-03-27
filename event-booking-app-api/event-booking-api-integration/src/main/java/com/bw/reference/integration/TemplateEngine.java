package com.bw.reference.integration;

import java.util.Map;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
public interface TemplateEngine {

    String getAsString(String templatePath, Map<String, Object> bindings);

    byte[] getBytes(String templatePath, Map<String, Object> bindings);
}
