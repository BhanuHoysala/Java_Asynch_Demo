/**
 * Java Asynchronous programming example with thenApply()
 */
public class ThenApplyDemo {

    public static void main(String[] args) throws InterruptedException {

        System.out.println(Thread.currentThread());

        CompletableFuture.supplyAsync(() -> {  // by default uses the ForkJoinPool

            try {
                System.out.println("Inside the Async execution");
                Thread.sleep(2000l);
                System.out.println("wait is over");
                System.out.println(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Success";
        }).thenApply(status -> {
            System.out.println(Thread.currentThread());
            System.out.println("Executing then apply 1");
            return "Success";
        }).thenApply(status -> {
            System.out.println("Executing then apply 2");
            return "Success";
        });

        System.out.println("Outside Completable (should be the printed first or second) ");

        Thread.sleep(3000l);
    }

}
