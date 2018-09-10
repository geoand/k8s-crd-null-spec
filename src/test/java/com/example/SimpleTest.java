package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.fabric8.kubernetes.api.model.KubernetesResourceList;
import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinition;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import me.snowdrop.istio.api.model.DoneableIstioResource;
import me.snowdrop.istio.api.model.IstioResource;
import org.junit.Test;

public class SimpleTest {

  private static final String CRD_NAME = "checknothings.config.istio.io";


  @Test
  public void run() throws Exception {
    final KubernetesClient client = new DefaultKubernetesClient();
    final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    final CustomResourceDefinition customResourceDefinition =
        client.customResourceDefinitions().withName(CRD_NAME).get();
    assertThat(customResourceDefinition).isNotNull();

    client
        .customResources(
            customResourceDefinition,
            IstioResource.class,
            KubernetesResourceList.class,
            DoneableIstioResource.class
        )
        .inNamespace(client.getNamespace())
        .create(
            objectMapper.readValue(
                this.getClass().getResource("/istio-resource.yml"),
                IstioResource.class
            )
        );
  }

}
