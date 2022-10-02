#!/usr/bin/env groovy

import com.example.Docker

def call(String imageBase, String imageBuild) {
    return new Docker(this).dockerPush(imageBase, imageBuild)
}