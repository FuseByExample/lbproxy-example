/*
 * Copyright (C) Red Hat, Inc.
 * http://www.redhat.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.examples.greeter.gateway;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.cxf.message.MessageContentsList;

public class ConcatenatingAggregationStrategy implements AggregationStrategy {

  private String separator;

  public String getSeparator() {
    if (separator == null) {
      separator = "";
    }
    return separator;
  }

  public void setSeparator(String separator) {
    this.separator = separator;
  }
  
  @Override
  public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
    if (oldExchange == null) {
      MessageContentsList body = newExchange.getIn().getBody(MessageContentsList.class);
      oldExchange = newExchange;
      oldExchange.getIn().setBody(body.get(0));
    } else {
      if (newExchange != null && !newExchange.isFailed()) {
        MessageContentsList body = newExchange.getIn().getBody(MessageContentsList.class);
        oldExchange.getIn().setBody(oldExchange.getIn().getBody() + getSeparator() + body.get(0));
      }
    }
    return oldExchange;
  }
}
