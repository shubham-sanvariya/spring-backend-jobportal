package com.shubh.jobportal.records;

public sealed interface StringOrLongValue permits StringOrLongValue.StringValue, StringOrLongValue.LongValue {
    record StringValue(String value) implements StringOrLongValue {}
    record LongValue(Long value) implements StringOrLongValue {}
}