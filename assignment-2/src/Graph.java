 import java.util.* ;
import java.io.* ;

public class Graph
{
    public static List <HashSet<Link>> adj = new ArrayList <HashSet<Link>> ();
    public static void main(String args[])
    {
        try
        {
            FileInputStream fstream= new FileInputStream(args[args.length-1]);
            DataInputStream in= new DataInputStream(fstream);
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            String line;
            String[] array;
            while((line=br.readLine())!=null) {
                array=line.split(" ");
                int node,node2,weight=1;
                // oi 2 komvoi
                node = Integer.parseInt(array[0]);
                node2 = Integer.parseInt(array[1]);
                //an uparxei 3o pedio, tote to varos pairnei tin timi tou 3ou pediou
                if(array.length==3)
                {
                    weight=Integer.parseInt(array[2]);
                }
                if(node>= adj.size())
                {
                    //edimiourgia kenwn thesewn sto adj
                    for(int i=adj.size();i<=node;i++)
                    {
                        adj.add(new HashSet<Link>());
                    }
                }
                HashSet<Link> h=adj.get(node);
                h.add(new Link (node2,weight));

                //mi kateuthinomeno
                if (args[0].equals("-u") )
                {
                    if(node2>= adj.size())
                    {
                        for(int i=adj.size();i<=node2;i++)
                        {
                            adj.add(new HashSet<Link>());
                        }
                    }
                    h=adj.get(node2);
                    h.add(new Link (node,weight));

                }
            }
        in.close();
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    int [] pq = new int [adj.size()];
    int k=0;
    for (int i = 0; i < args.length-1; i++)
    {
        if (args[i].equals("-s"))
        {
           //epilogi -s, kaleite h methodos dijkstra
           k=i+1;
           int [] pred = new int [adj.size()];
           int [] dist = new int [adj.size()];
           dijkstra(pq,Integer.parseInt(args[k]),pred,dist);
           //ektuposi pinaka me tous komvous pou proigountai
           System.out.print("Predecessor matrix\n[");
           for(i=0;i<pred.length-1;i++)
           {
               System.out.printf("%d, ",pred[i]);
           }
           System.out.printf("%d]\n",pred[pred.length-1]);
           //ektuposi pinaka me tis apostaseis
           System.out.print("Distance matrix\n[");
           for(i=0;i<dist.length-1;i++)
           {
               System.out.printf("%d, ",dist[i]);
           }
           System.out.printf("%d]\n",dist[dist.length-1]);
        }
        else if (args[i].equals("-a"))
        {
            //epilogi -a, kaleite h methodos pairsShortest
            int [][] pred = new int [adj.size()][adj.size()];
            int [][] dist = new int [adj.size()][adj.size()];
            pairsShortest(pq,pred,dist);
            System.out.print("Predecessor matrix\n");
            for( i=0;i<pred.length;i++)
            {
                System.out.print("[");
                for(int j=0;j<=pred.length-2;j++)
                {
                    System.out.printf("%d, ",pred[i][j]);
                }
                System.out.printf("%d]\n",pred[i][pred.length-1]);
            }
            System.out.print("Distance matrix\n[");
            for(i=0;i<dist.length;i++)
            {
                System.out.print("[");
                for(int j=0;j<=dist.length-2;j++)
                            {
                                System.out.printf("%d, ",dist[i][j]);
                            }
                            System.out.printf("%d]\n",dist[i][dist.length-1]);
            }

        }
        else if (args[i].equals("-d"))
        {
            //epilogi -d, kaleite h methodos diametros
            int [][] pred = new int [adj.size()][adj.size()];
            int [][] dist = new int [adj.size()][adj.size()];
            int max=diametros(pq,pred,dist);
            System.out.println(max);
        }
    }

   }

   //upologizw sintomotero monopati apo enan komvo
   public static void dijkstra(int [] pq,int s,int [] pred,int [] dist)
   {
       for(int v=0; v<adj.size(); v++)
       {
           if(v!=s)
           {
               dist[v]=Integer.MAX_VALUE;
           }
           else
           {
               dist[v]=0;
           }
           pq[v]=dist[v];
       }
       int l=0;
       while( pq.length!=l)
       {
           //ExtractMin
            int min=Integer.MAX_VALUE;
            int j=0;
            for(int i=0;i<pq.length;i++)
            {
                      if(pq[i]<min && pq[i]!=-1)
                      {
                          min=pq[i];
                          j=i;
                      }
            }
            pq[j]=-1;
            l=0;
            for(int i=0;i<pq.length;i++)
            {
                if(pq[i]==-1)
                {
                    l++;
                }
          }
           HashSet<Link> list = adj.get(j);
           pred[s]=-1;
           for(Link link:list)
           {
               if (dist[link.node]>dist[j]+link.weight)
               {
                   dist[link.node]=dist[j]+link.weight;
                   pred[link.node]=j;
                   pq[link.node]=dist[link.node];
              }
           }
       }
   }


   //Vriskoume ta sintomotera monopatia metaksu twn zeugwn
   public static void pairsShortest(int [] pq,int [][] pred, int [][]dist)
   {
       for(int i=0;i<adj.size();i++)
       {
           for (int j=0;j<adj.size();j++)
           {
               dist[i][j]=0;
           }
       }
       for(int i=0;i<adj.size();i++)
       {
           dijkstra(pq,i,pred[i],dist[i]);
       }
   }


   //Vriskoume tin diametro tou grafou,dhladh to mikos tou makriterou sintomoterou monopatiou
   public static int diametros(int [] pq,int [][] pred, int [][]dist)
   {
       pairsShortest(pq,pred,dist);
       int max=-1;
       for(int i=0;i<dist.length;i++)
       {
           for(int j=0;j<dist.length;j++)
           {
               if(dist[i][j]>max)
               {
                   max=dist[i][j];
               }
           }
       }
       return max;
   }
}