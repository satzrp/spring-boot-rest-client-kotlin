package com.archospark.springbootrestclientkotlin.controller

import com.archospark.springbootrestclientkotlin.model.Post
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import kotlin.system.measureTimeMillis

@RestController
@RequestMapping(value = "/posts")
class PostController(private val restTemplate: RestTemplate, private val logger: Logger) {

    @Value("\${api.root.url}")
    private val baseURL: String? = null

    private val postIds = 1..10

    @GetMapping(value = "/all")
    fun getPosts() : List<Post> {
        var posts : List<Post> = listOf()
        val duration = measureTimeMillis {
            posts = postIds.map { postId -> restTemplate.getForObject("$baseURL/posts/$postId", Post::class.java) }
            posts.forEach { (_, id, title) -> println("$id : $title") }
        }
        logger.info("getPosts method execution elapsed time: $duration ms")
        return posts
    }

    @GetMapping(value = "/async/all")
    fun getPostsAsync() : List<Post> {
        var posts : List<Post> = listOf()
        fun asyncGet(postId : Int) = async(CommonPool) {
            restTemplate.getForObject("$baseURL/posts/$postId", Post::class.java)
        }
        val postsAsyncCalls = postIds.map { asyncGet(it) }
        val duration = measureTimeMillis {
            posts = runBlocking {
                postsAsyncCalls.map { it.await() }
            }
        }
        posts.forEach { (_, id, title) -> println("$id : $title") }
        logger.info("getPostsAsync method execution elapsed time: $duration ms")
        return posts
    }
}