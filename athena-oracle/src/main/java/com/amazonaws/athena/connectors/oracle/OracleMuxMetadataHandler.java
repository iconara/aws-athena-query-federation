/*-
 * #%L
 * athena-mysql
 * %%
 * Copyright (C) 2019 Amazon Web Services
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package com.amazonaws.athena.connectors.oracle;

import com.amazonaws.athena.connectors.jdbc.MultiplexingJdbcMetadataHandler;
import com.amazonaws.athena.connectors.jdbc.connection.DatabaseConnectionConfig;
import com.amazonaws.athena.connectors.jdbc.connection.JdbcConnectionFactory;
import com.amazonaws.athena.connectors.jdbc.manager.JdbcMetadataHandler;
import com.amazonaws.athena.connectors.jdbc.manager.JdbcMetadataHandlerFactory;
import com.amazonaws.services.athena.AmazonAthena;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import org.apache.arrow.util.VisibleForTesting;

import java.util.Map;

class OracleMetadataHandlerFactory
        implements JdbcMetadataHandlerFactory
{
    @Override
    public String getEngine()
    {
        return OracleConstants.ORACLE_NAME;
    }

    @Override
    public JdbcMetadataHandler createJdbcMetadataHandler(DatabaseConnectionConfig config)
    {
        return new OracleMetadataHandler(config);
    }
}

public class OracleMuxMetadataHandler
        extends MultiplexingJdbcMetadataHandler
{
    public OracleMuxMetadataHandler()
    {
        super(new OracleMetadataHandlerFactory());
    }

    @VisibleForTesting
    protected OracleMuxMetadataHandler(final AWSSecretsManager secretsManager, final AmazonAthena athena, final JdbcConnectionFactory jdbcConnectionFactory,
                                       final Map<String, JdbcMetadataHandler> metadataHandlerMap, final DatabaseConnectionConfig databaseConnectionConfig)
    {
        super(secretsManager, athena, jdbcConnectionFactory, metadataHandlerMap, databaseConnectionConfig);
    }
}