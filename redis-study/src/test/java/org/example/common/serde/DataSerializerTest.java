package org.example.common.serde;

import static org.assertj.core.api.Assertions.assertThat;

import org.example.redisstudy.common.serde.DataSerializer;
import org.junit.jupiter.api.Test;

class DataSerializerTest {

    @Test
    void serde() {

        MyData myData = new MyData("id", "data");
        String serialized = DataSerializer.serializeOrException(myData);

        MyData deserialized = DataSerializer.deserializeOrNull(serialized, MyData.class);
        assertThat(myData).isEqualTo(deserialized);
    }


    record MyData (
            String id, String data
    ) {

    }
}