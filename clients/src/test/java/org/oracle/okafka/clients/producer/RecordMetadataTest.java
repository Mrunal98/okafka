/*
** OKafka Java Client version 23.4.
**
** Copyright (c) 2019, 2024 Oracle and/or its affiliates.
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

/*
 * 04/20/2020: This file is modified to support Kafka Java Client compatability to Oracle Transactional Event Queues.
 *
 */

package org.oracle.okafka.clients.producer;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.record.DefaultRecord;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RecordMetadataTest {

    @Test
    @SuppressWarnings("deprecation")
    public void testConstructionWithMissingRelativeOffset() {
        TopicPartition tp = new TopicPartition("foo", 0);
        long timestamp = 2340234L;
        int keySize = 3;
        int valueSize = 5;
        Long checksum = -1L;

        RecordMetadata metadata = new RecordMetadata(tp, -1L, -1L, timestamp, checksum, keySize, valueSize);
        assertEquals(tp.topic(), metadata.topic());
        assertEquals(tp.partition(), metadata.partition());
        assertEquals(timestamp, metadata.timestamp());
        assertFalse(metadata.hasOffset());
        assertEquals(-1L, metadata.offset());
        assertEquals(checksum.longValue(), metadata.checksum());
        assertEquals(keySize, metadata.serializedKeySize());
        assertEquals(valueSize, metadata.serializedValueSize());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testConstructionWithRelativeOffset() {
        TopicPartition tp = new TopicPartition("foo", 0);
        long timestamp = 2340234L;
        int keySize = 3;
        int valueSize = 5;
        long baseOffset = 15L;
        long relativeOffset = 3L;
        Long checksum = -1L;

        RecordMetadata metadata = new RecordMetadata(tp, baseOffset, relativeOffset, timestamp, checksum,
                keySize, valueSize);
        assertEquals(tp.topic(), metadata.topic());
        assertEquals(tp.partition(), metadata.partition());
        assertEquals(timestamp, metadata.timestamp());
        assertEquals((baseOffset << 16) + (relativeOffset), metadata.offset());
        assertEquals(checksum.longValue(), metadata.checksum());
        assertEquals(keySize, metadata.serializedKeySize());
        assertEquals(valueSize, metadata.serializedValueSize());
    }

}
