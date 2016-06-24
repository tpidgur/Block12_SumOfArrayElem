import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Зая on 24.06.2016.
 */
public class SumOfArrayElem {
   static int arr[]={0, 1, 2, 3, 4, 5, 6, 7, 8, 9};



    private static final int NUM_THREADS=5;
    static  class RecSum extends RecursiveTask <Integer> {
        int from,to;

        public RecSum(int from, int to) {
            this.from = from;
            this.to = to;
        }
        public Integer compute(){
            if ((to-from)<=arr.length/NUM_THREADS){
                int localSum=0;
                for (int i=from;i<=to;i++){
                    localSum+=i;
                }
                System.out.printf("\tSumming of range %d to %d is %d%n",from,to,localSum);
                return localSum;
            }else{
                int mid=(from+to)/2;
                System.out.printf("Forking in two ranges: %d to %d and %d to %d%n",from,mid,mid+1,to);
                RecSum firstHalf=new RecSum(from,mid);
                firstHalf.fork();
                RecSum secondHalf = new RecSum(mid+1,to);
                int resultSecond=secondHalf.compute();
                return firstHalf.join()+resultSecond;
            }
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool=new ForkJoinPool(NUM_THREADS);
        int computedSum=pool.invoke(new RecSum(0,arr.length-1));
        System.out.printf("Sum for range 1..%d; computed sum=%d%n",arr.length-1, computedSum);
    }
}
