package org.briarproject.briar.headless.forums

import org.briarproject.briar.headless.json.JsonDict
import org.briarproject.masterproject.api.forum.Forum

internal fun Forum.output() = JsonDict(
    "name" to name,
    "id" to id.bytes
)

internal fun Collection<Forum>.output() = map { it.output() }
