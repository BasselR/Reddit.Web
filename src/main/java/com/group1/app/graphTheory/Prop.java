/**
 * @file Prop.java
 * @author Jared Bentvelsen
 * @date 07/04/2020
 * @brief Enumeration for looking up properties in an Edge object
 */
package com.group1.app.graphTheory;

// The following are properties of a connecting reddit post
public enum Prop {
    CHARS, // Number of characters
    WORDS, // Number of words
    POS_SENT, // Positive Sentiment score
    NEG_SENT, // Negative Sentiment score
    COM_SENT, // Compound Sentiment score
    SWEAR, // Swear word score
    POS_EMO, // Positive emotion score
    NEG_EMO, // Negative emotion score
    SEX, // Sexual word score
    ANGER, // Angry word score
    SAD, // Sad word score
    RELIG, // Religious word score
    DEATH, // Death word score
    MONEY, // Money word score
}