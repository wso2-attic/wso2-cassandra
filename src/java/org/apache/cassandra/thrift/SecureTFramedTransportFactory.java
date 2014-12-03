package org.apache.cassandra.thrift;

/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.thrift.transport.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class SecureTFramedTransportFactory implements ITransportFactory
{
    public TTransport openTransport(String host, int port, Configuration conf) throws TTransportException
    {
        TSSLTransportFactory.TSSLTransportParameters tsslTransportParameters = new TSSLTransportFactory.TSSLTransportParameters();
        tsslTransportParameters.setTrustStore(conf.get("ssl.truststore", null), conf.get("ssl.truststore.password", null));

        TTransport transport = new TFramedTransport(TSSLTransportFactory.getClientSocket(host, port, 30000, tsslTransportParameters));
        return transport;
    }

    public void setOptions(Map<String, String> options)
    {
    }

    public Set<String> supportedOptions()
    {
        return Collections.emptySet();
    }
}
