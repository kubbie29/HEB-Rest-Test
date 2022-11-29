package dev.bponder.heb.model

import io.circe.Json

case class Image(
    imageId: String,
    imageLabel: String,
    imageData: Array[Byte],
    objectsJson: Json,
    metadata: Json
)
