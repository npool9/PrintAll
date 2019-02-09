package edu.ncsu.nmpool;

import java.util.logging.Level; 
import java.util.logging.Logger; 
 
import javax.print.DocPrintJob; 
import javax.print.event.PrintJobAdapter; 
import javax.print.event.PrintJobEvent; 
 
/**
 * Mointor for print job. 
 *  
 * @author Ahmed Moustafa (ahmed@users.sf.net) 
 */ 
 
public class PrintJobMonitor { 
 /**
  * Logger 
  */ 
 private static final Logger logger = Logger.getLogger(Controller.class.getName()); 
 
    // True iff it is safe to close the print job's input stream 
    private boolean done = false; 
     
    PrintJobMonitor(DocPrintJob job) { 
        // Add a listener to the print job 
        job.addPrintJobListener(new PrintJobAdapter() { 
            public void printJobCanceled(PrintJobEvent printJobEvent) { 
                logger.info("Print job canceled"); 
             allDone(); 
            } 
             
            public void printJobCompleted(PrintJobEvent printJobEvent) { 
             logger.info("Print job completed"); 
                allDone(); 
            } 
             
            public void printJobFailed(PrintJobEvent printJobEvent) { 
             logger.info("Print job failed"); 
                allDone(); 
            } 
 
            public void printJobNoMoreEvents(PrintJobEvent printJobEvent) { 
                allDone(); 
            } 
 
            void allDone() { 
                synchronized (PrintJobMonitor.this) { 
                    done = true; 
                    PrintJobMonitor.this.notify(); 
                } 
            } 
        }); 
    } 
 
    /**
     * Waits for print job 
     * 
     */ 
    public synchronized void waitForPrintJob() { 
        try { 
         logger.info("Waiting for print job..."); 
            while (!done) { 
                wait(); 
            } 
            logger.info("Finished waiting for print"); 
        } catch (InterruptedException e) { 
         logger.log(Level.SEVERE, "Failed waiting for print job: " + e.getMessage(), e); 
        } 
    } 
}
