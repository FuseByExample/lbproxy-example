/*
 * Copyright 2016 Red Hat, Inc.
 * <p>
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package com.redhat.examples.greeter.gateway;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CamelConfiguration extends RouteBuilder {

  private static final Logger log = LoggerFactory.getLogger(CamelConfiguration.class);
  
  @Autowired
  protected GatewayProperties config;
  
  @Bean
  protected AggregationStrategy concatenatingAggregationStrategy() {
    ConcatenatingAggregationStrategy aggregationStrategy = new ConcatenatingAggregationStrategy();
    aggregationStrategy.setSeparator(", ");
    return aggregationStrategy;
  }
  
  @Override
  public void configure() throws Exception {
    
    from("cxf:/greeter?serviceClass=com.redhat.examples.greeter.Greeter&loggingFeatureEnabled=true")
      .recipientList()
        .constant(config.getBackendUrls())
        .aggregationStrategyRef("concatenatingAggregationStrategy")
        .parallelProcessing(true)
      .end()
    ;
  }
}
