#!/usr/bin/env groovy

import com.example.Docker

def call(String imageBuild, String imageLatest) {
    return new Docker(this).dockerPush(imageBuild, imageLatest)
}