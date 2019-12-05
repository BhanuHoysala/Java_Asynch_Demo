package de.springmedia.asyncdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.web.client.RestTemplate;

public class AsyncDemo
{

    public static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args)
    {

        final List<String> urls = new ArrayList<>();
        urls.add("http://jsonplaceholder.typicode.com/users/1");
        urls.add("http://jsonplaceholder.typicode.com/posts?userId=1");

        final ExecutorService executorService = Executors.newFixedThreadPool(urls.size());
        final List<CompletableFuture> completableFuturesList = new ArrayList<>();

        for (final String url : urls)
        {
            completableFuturesList.add(CompletableFuture.supplyAsync(() ->
                restTemplate.getForEntity(url, String.class).getBody()
            ));
        }

        // Wait until they are all done
        CompletableFuture.allOf(
            completableFuturesList.toArray(new CompletableFuture[completableFuturesList.size()])).join();

        executorService.shutdown();

        completableFuturesList.forEach(response -> {
            // Logic to merge the results ?
            try
            {
                // Logging the response
                System.out.println(response.get());
            }
            catch (Exception e)
            {
            }
        });
    }
}
