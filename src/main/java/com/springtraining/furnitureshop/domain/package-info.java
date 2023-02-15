@org.hibernate.annotations.GenericGenerator(
        name = "ID_GENERATOR",
        strategy = "enhanced-sequence",
        parameters = {
                @org.hibernate.annotations.Parameter(
                        name = "sequence_name",
                        value = "sequence_table"
                ),
                @org.hibernate.annotations.Parameter(
                        name = "initial_value",
                        value = "500"
                )
        })
package com.springtraining.furnitureshop.domain;
