package io.cantu;

import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class HolaControllerSpec extends Specification {
    @Shared
    @AutoCleanup
    ConfigurableApplicationContext context

    @Shared
    def baseUrl = "http://localhost:8080/hola"

    void setupSpec() {
        Future future = Executors
                        .newSingleThreadExecutor().submit(
                        new Callable() {
                            @Override
                            public ConfigurableApplicationContext call() throws Exception {
                                return (ConfigurableApplicationContext) SpringApplication
                                        .run(RestApiApplication.class)
                            }
                        })
        context = future.get(60, TimeUnit.SECONDS)
    }

    def '/hola'() {
        when:
        def entity = new RestTemplate().getForEntity(url, String.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body == response

        where:
        url                         || response
        baseUrl                     || "Hola, Chicos"
        "${baseUrl}?nombre=Dora"    || "Hola, Dora"
        "${baseUrl}?nombre=Diego"   || "Hola, Diego"
    }
}
