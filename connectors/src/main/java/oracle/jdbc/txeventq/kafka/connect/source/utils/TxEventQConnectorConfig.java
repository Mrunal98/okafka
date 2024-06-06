/*
** Kafka Connect for TxEventQ.
**
** Copyright (c) 2023, 2024 Oracle and/or its affiliates.
** Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl.
*/

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package oracle.jdbc.txeventq.kafka.connect.source.utils;

import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxEventQConnectorConfig extends AbstractConfig {
    private static final Logger log = LoggerFactory.getLogger(TxEventQConnectorConfig.class);

    // TEQ Configuration
    public static final String DATABASE_TNS_ALIAS_CONFIG = "db_tns_alias";
    private static final String DATABASE_TNS_ALIAS_DOC = "The TNS alias name placed in the tnsnames.ora for the database to connect to.";
    private static final String DATABASE_TNS_ALIAS_DISPLAY = "TNS alias used for JDBC connection.";

    public static final String DATABASE_WALLET_CONFIG = "wallet.path";
    private static final String DATABASE_WALLET_DOC = "The directory to the wallet information.";
    private static final String DATABASE_WALLET_DISPLAY = "wallet.path";

    public static final String DATABASE_TNSNAMES_CONFIG = "tnsnames.path";
    private static final String DATABASE_TNSNAMES_DOC = "The directory to where the tnsnames.ora file is located.";
    private static final String DATABASE_TNSNAMES_DISPLAY = "tnsnames.path";

    public static final String TXEVENTQ_QUEUE_NAME = "txeventq.queue.name";
    public static final String TXEVENTQ_QUEUE_NAME_DOC = "The name of the TxEventQ queue where the connector reads from.";
    public static final String TXEVENTQ_QUEUE_NAME_DISPLAY = "txeventq.queue.name";

    public static final String TXEVENTQ_SUBSCRIBER_CONFIG = "txeventq.subscriber";
    private static final String TXEVENTQ_SUBSCRIBER_DOC = "txeventq.subscriber";
    private static final String TXEVENTQ_SUBSCRIBER_DISPLAY = "txeventq.subscriber";

    // Kafka Configuration
    public static final String KAFKA_TOPIC = "kafka.topic";
    public static final String KAFKA_TOPIC_DOC = "The name of the Kafka topic where the connector writes all records that were read from the JMS broker.";
    public static final String KAFKA_TOPIC_DISPLAY = "Target Kafka topic";

    public static final String BOOTSTRAP_SERVERS_CONFIG = CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
    private static final String BOOTSTRAP_SERVERS_DOC = CommonClientConfigs.BOOTSTRAP_SERVERS_DOC;
    private static final String BOOTSTRAP_SERVERS_DISPLAY = "";

    // Kafka Connect Task
    public static final String KAFKA_CONNECT_NAME = "name";
    private static final String KAFKA_CONNECT_NAME_DOC = "";
    private static final String KAFKA_CONNECT_NAME_DISPLAY = "";

    public static final String KAFKA_CONNECT_TASK_ID = "source-task.id";
    private static final String KAFKA_CONNECT_TASK_ID_DOC = "";
    private static final String KAFKA_CONNECT_TASK_ID_DISPLAY = "";

    public static final String TASK_BATCH_SIZE_CONFIG = "batch.size";
    private static final String TASK_BATCH_SIZE_DISPLAY = "Batch Size";
    private static final String TASK_BATCH_SIZE_DOC = "The maximum number of records that a connector task may read from the Oracle TxEventQ broker before writing to Kafka. The task holds these records until they are acknowledged in Kafka, so this may affect memory usage.";
    public static final int TASK_BATCH_SIZE_DEFAULT = 1024;

    public static final String TASK_MAX_CONFIG = "tasks.max";
    private static final String TASK_MAX_DISPLAY = "Tasks Max";
    private static final String TASK_MAX_DOC = "Maximum number of tasks to use for this connector.";
    public static final int TASK_MAX_DEFAULT = 1;

    public final String topic;

    public TxEventQConnectorConfig(Map<String, String> originals) {
        super(getConfig(), originals);
        this.topic = getString("kafka.topic");
    }

    public TxEventQConnectorConfig(ConfigDef definition, Map<String, String> originals) {
        super(definition, originals);
        this.topic = getString("kafka.topic");
    }

    public TxEventQConnectorConfig(ConfigDef definition, Map<String, String> originals,
            boolean doLog) {
        super(definition, originals, doLog);
        this.topic = getString("kafka.topic");
    }

    public static ConfigDef getConfig() {
        ConfigDef configDef = new ConfigDef();

        int orderInGroup = 0;

        // Database Group Configurations
        String groupName = "Database";

        configDef.define(DATABASE_TNS_ALIAS_CONFIG, ConfigDef.Type.STRING, "",
                ConfigDef.Importance.HIGH, DATABASE_TNS_ALIAS_DOC, groupName, ++orderInGroup,
                ConfigDef.Width.LONG, DATABASE_TNS_ALIAS_DISPLAY);

        configDef.define(DATABASE_WALLET_CONFIG, ConfigDef.Type.STRING, "",
                ConfigDef.Importance.HIGH, DATABASE_WALLET_DOC, groupName, ++orderInGroup,
                ConfigDef.Width.MEDIUM, DATABASE_WALLET_DISPLAY);

        configDef.define(DATABASE_TNSNAMES_CONFIG, ConfigDef.Type.STRING, "",
                ConfigDef.Importance.HIGH, DATABASE_TNSNAMES_DOC, groupName, ++orderInGroup,
                ConfigDef.Width.MEDIUM, DATABASE_TNSNAMES_DISPLAY);

        // TxEventQ Group Configurations
        groupName = "TxEventQ";
        orderInGroup = 0;

        configDef.define(TXEVENTQ_QUEUE_NAME, ConfigDef.Type.STRING, "", ConfigDef.Importance.HIGH,
                TXEVENTQ_QUEUE_NAME_DOC, groupName, ++orderInGroup, ConfigDef.Width.MEDIUM,
                TXEVENTQ_QUEUE_NAME_DISPLAY);

        configDef.define(TXEVENTQ_SUBSCRIBER_CONFIG, ConfigDef.Type.STRING, "",
                ConfigDef.Importance.HIGH, TXEVENTQ_SUBSCRIBER_DOC, groupName, ++orderInGroup,
                ConfigDef.Width.MEDIUM, TXEVENTQ_SUBSCRIBER_DISPLAY);

        // KAFKA Group Configurations
        groupName = "kafka";
        orderInGroup = 0;

        configDef.define(KAFKA_TOPIC, ConfigDef.Type.STRING, "", ConfigDef.Importance.HIGH,
                KAFKA_TOPIC_DOC, groupName, ++orderInGroup, ConfigDef.Width.LONG,
                KAFKA_TOPIC_DISPLAY);

        configDef.define(KAFKA_CONNECT_NAME, ConfigDef.Type.STRING, "", ConfigDef.Importance.HIGH,
                KAFKA_CONNECT_NAME_DOC, groupName, ++orderInGroup, ConfigDef.Width.MEDIUM,
                KAFKA_CONNECT_NAME_DISPLAY);

        configDef.define(KAFKA_CONNECT_TASK_ID, ConfigDef.Type.STRING, "",
                ConfigDef.Importance.HIGH, KAFKA_CONNECT_TASK_ID_DOC, groupName, ++orderInGroup,
                ConfigDef.Width.MEDIUM, KAFKA_CONNECT_TASK_ID_DISPLAY);

        configDef.define(BOOTSTRAP_SERVERS_CONFIG, ConfigDef.Type.LIST, ConfigDef.Importance.HIGH,
                BOOTSTRAP_SERVERS_DOC, groupName, ++orderInGroup, ConfigDef.Width.MEDIUM,
                BOOTSTRAP_SERVERS_DISPLAY);

        configDef.define(TASK_BATCH_SIZE_CONFIG, ConfigDef.Type.INT, TASK_BATCH_SIZE_DEFAULT,
                ConfigDef.Importance.MEDIUM, TASK_BATCH_SIZE_DOC, groupName, ++orderInGroup,
                ConfigDef.Width.MEDIUM, TASK_BATCH_SIZE_DISPLAY);

        configDef.define(TASK_MAX_CONFIG, ConfigDef.Type.INT, TASK_MAX_DEFAULT,
                ConfigDef.Importance.HIGH, TASK_MAX_DOC, groupName, ++orderInGroup,
                ConfigDef.Width.MEDIUM, TASK_MAX_DISPLAY);

        return configDef;
    }

    public String name() {
        return getString(KAFKA_CONNECT_NAME);
    }
}
