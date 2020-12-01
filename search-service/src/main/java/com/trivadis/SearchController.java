package com.trivadis;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RestController
public class SearchController {

    private TreeSet<Book> allBooks = new TreeSet<>();

    public SearchController(DiscoveryClient discoveryClient) {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new AlwaysRetryPolicy());

        try {
            Arrays.stream(retryTemplate.execute(new RetryCallback<Book[], Throwable>() {
                @Override
                public Book[] doWithRetry(RetryContext context) throws Throwable {
                    List<ServiceInstance> instances = discoveryClient.getInstances("master-data-service");
                    String url = instances.stream().findFirst().get().getUri().toString();
                    return new RestTemplate().getForObject(url + "/book", Book[].class);
                }
            })).forEach(allBooks::add);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    // http://localhost:8080/search?q=Welt
    @GetMapping("/search")
    public List<Book> search(@RequestParam("q") String query, @RequestParam(value="limit", required = false, defaultValue = "10") int limit) {
        return allBooks.stream() //
                .filter(b -> b.getName().toLowerCase().contains(query.toLowerCase()) || b.getAuthor().toLowerCase().contains(query.toLowerCase())) //
                .limit(limit)
                .collect(Collectors.toList());
    }
}
