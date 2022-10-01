#!/usr/bin/env groovy

import com.example.Docker

def call(String imageBase) {
    return new Docker(this).dockerPush(imageBase)
}