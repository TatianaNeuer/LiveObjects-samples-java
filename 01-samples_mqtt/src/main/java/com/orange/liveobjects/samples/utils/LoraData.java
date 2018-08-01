/*
 * Copyright (C) 2016 Orange
 *
 * This software is distributed under the terms and conditions of the 'BSD-3-Clause'
 * license which can be found in the file 'LICENSE.txt' in this package distribution
 * or at 'https://opensource.org/licenses/BSD-3-Clause'.
 */
package com.orange.liveobjects.samples.utils;

import java.util.List;

/**
 * Structure of a "data message" that can be sent by a LoRa device into Live Objects.
 */
public class LoraData {

    /**
     * Stream identifier: urn:lo:nsid:lora:<devEUI>
     */
    public String streamId;

    /**
     * timestamp (ISO8601 format)
     */
    public String timestamp;

    /**
     * Data "model" of the field "value"
     */
    public String model;

    /**
     * Value
     */
    public LoraDataValue value;

    /**
     * Tags
     */
    public List<String> tags;

    /*
    * Metadata
     */
    public LoraMetadata metadata;

    /**
     * creation date (ISO8601 format)
     */
    public String created;


    @Override
    public String toString() {
        return "LoraData{" +
                "streamId='" + streamId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", model='" + model + '\'' +
                ", value=" + value +
                ", tags=" + tags +
                ", metadata=" + metadata +
                ", created=" + created +
                '}';
    }


    // *********************
    // *** INNER CLASSES ***
    // *********************

    public class LoraDataValue {
        /**
         * Hexadecimal raw data of the message
         */
        public String payload;

        @Override
        public String toString() {
            return "LoraDataValue{" +
                    "payload=" + payload +
                    '}';
        }
    }

    public class Group {
        /**
         * Group unique identifier
         */
        public String id;

        /**
         * Complete group path
         */
        public String path;

        @Override
        public String toString() {
            return "Group{" +
                    "id=" + id +
                    ", path=" + path +
                    '}';
        }
    }

    public class Location {

        /**
         * Latitude
         */
        public Double lat;

        /**
         * Longitude
         */
        public Double lon;

        /**
         * Altitude
         */
        public Double alt;

        /**
         * Measure accuracy
         */
        public Double accuracy;

        /**
         * Location provider
         */
        public String provider;

        @Override
        public String toString() {
            return "Location{" +
                    "lat=" + lat +
                    ", lon=" + lon +
                    ", alt=" + alt +
                    ", accuracy=" + accuracy +
                    ", provider=" + provider +
                    '}';
        }
    }

    public class LoraNetwork {

        /**
         * Device EUI
         */
        public String devEUI;

        /**
         * Port of the device on which the command was sent
         */
        public Integer port;

        /**
         * Uplink frame counter of the message
         */
        public Integer fcnt;

        /**
         * Received signal strength indication measured by the best gateway
         */
        public Double rssi;

        /**
         * Signal noise ratio measured by the best gateway
         */
        public Double snr;

        /**
         * Spreading factor used by the device
         */
        public Integer sf;

        /**
         * Signal quality indicator from 1 to 5
         */
        public Integer signalLevel;

        /**
         * Location of the device
         */
        public Location location;

        /**
         * Number of Lora gateways that have received the message
         */
        public Integer gatewayCnt;

        @Override
        public String toString() {
            return "LoraNetwork{" +
                    "devEUI=" + devEUI +
                    ", port=" + port +
                    ", fcnt=" + fcnt +
                    ", rssi=" + rssi +
                    ", snr=" + snr +
                    ", sf=" + sf +
                    ", signalLevel=" + signalLevel +
                    ", location=" + location +
                    ", gatewayCnt=" + gatewayCnt +
                    '}';
        }

    }

    public class Network {
        /**
         * Information from lora network
         */
        public LoraNetwork lora;

        @Override
        public String toString() {
            return "Network{" +
                    "lora='" + lora + '\'' +
                    '}';
        }
    }

    public class LoraMetadata {

        /**
         * Source of the payload: urn:lo:nsid:lora:<devEUI>
         */
        public String source;

        /**
         * Group to which the device belongs
         */
        public Group group;

        /**
         * Encoding type of the binary payload sent by the device
         */
        public String encoding;

        /**
         * Entry point of the payload: lora
         */
        public String connector;

        /**
         * Information from network
         */
        public Network network;

        @Override
        public String toString() {
            return "LoraMetadata{" +
                    "source='" + source + '\'' +
                    ", group='" + group + '\'' +
                    ", encoding='" + encoding + '\'' +
                    ", connector='" + connector + '\'' +
                    ", network='" + network + '\'' +
                    '}';
        }

    }

}
