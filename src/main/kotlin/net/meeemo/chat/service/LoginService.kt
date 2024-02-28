package net.meeemo.chat.service

import com.fasterxml.jackson.databind.JsonNode
import net.meeemo.chat.model.entity.user.ChatUser
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate


@Service
@PropertySource("classpath:application-oauth.yml")
class LoginService(
    private val env: Environment,
) {
    private val restTemplate = RestTemplate()
    private val log = LoggerFactory.getLogger(LoginService::class.java)
//    fun socialLogin(code: String, registrationId: String) {
//        println("code = $code")
//        println("registrationId = $registrationId")
//    }



//    fun LoginService(env: Environment?) {
//        this.env = env
//    }

    fun socialLogin(code: String, registrationId: String) {
        System.out.println("code = $code")
        System.out.println("registrationId = $registrationId")
//        log.info("code = {}", code)
//        log.info("registrationId = {}", registrationId)
//        log.info("======================================================")
        val accessToken = getAccessToken(code, registrationId)
//        log.info("accessToken = {}", accessToken)
        val userResourceNode = getUserResource(accessToken, registrationId)

//        log.info("userResourceNode = {}", userResourceNode?.asText())
        val userResource: ChatUser?
        when (registrationId) {
            "google" -> {
                val id = userResourceNode!!["id"].asText()
                val email = userResourceNode["email"].asText()
                val nickname = userResourceNode["name"].asText()
                userResource = ChatUser(id, email, nickname)
            }

//            "kakao" -> {
//                userResource.setId(userResourceNode!!["id"].asText())
//                userResource.setEmail(userResourceNode["kakao_account"]["email"].asText())
//                userResource.setNickname(userResourceNode["kakao_account"]["profile"]["nickname"].asText())
//            }
//
//            "naver" -> {
//                userResource.setId(userResourceNode!!["response"]["id"].asText())
//                userResource.setEmail(userResourceNode["response"]["email"].asText())
//                userResource.setNickname(userResourceNode["response"]["nickname"].asText())
//            }

            else -> {
                userResource = null
                throw RuntimeException("UNSUPPORTED SOCIAL TYPE")
            }
        }
        log.info("userResource = {}", userResource)
        log.info("id = {}", userResource.id)
        log.info("email = {}", userResource.email)
        log.info("nickname {}", userResource.nickname)
        log.info("======================================================")
    }

    private fun getAccessToken(authorizationCode: String, registrationId: String): String {
        val clientId = env.getProperty("spring.security.oauth2.client.registration.$registrationId.client-id")
        val clientSecret = env.getProperty("spring.security.oauth2.client.registration.$registrationId.client-secret")
        val redirectUri = env.getProperty("spring.security.oauth2.client.registration.$registrationId.redirect-uri")
        val tokenUri = env.getProperty("spring.security.oauth2.client.registration.$registrationId.token-uri") ?: ""
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val logStr = String.format("clientId = %s, clientSecret = %s, redirectUri = %s, tokenUri = %s%n", clientId, clientSecret, redirectUri, tokenUri)
        log.info(logStr)
        System.out.printf(logStr)
        params.add("code", authorizationCode)
        params.add("client_id", clientId)
        params.add("client_secret", clientSecret)
        params.add("redirect_uri", redirectUri)
        params.add("grant_type", "authorization_code")
        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)
        val entity: HttpEntity<Any> = HttpEntity<Any>(params, headers)
        val responseNode = restTemplate.exchange(
            tokenUri, HttpMethod.POST, entity,
            JsonNode::class.java
        )
        val accessTokenNode = responseNode.body
        return accessTokenNode!!["access_token"].asText()
//        return "accessTokenNode!![\"access_token\"].asText()"
    }

    private fun getUserResource(accessToken: String, registrationId: String): JsonNode? {
        val resourceUri = env.getProperty("spring.security.oauth2.client.registration.$registrationId.resource-uri")
        log.info("resourceUri = {}", resourceUri)
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $accessToken")
        val entity: HttpEntity<*> = HttpEntity<Any?>(headers)
        return restTemplate.exchange(resourceUri!!, HttpMethod.GET, entity, JsonNode::class.java).body
    }
}