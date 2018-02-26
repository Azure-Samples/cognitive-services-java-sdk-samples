package com.microsoft.azure.contentmoderator.samples;

import com.microsoft.azure.cognitiveservices.contentmoderator.DetectedTerms;
import com.microsoft.azure.cognitiveservices.contentmoderator.TermsData;
import com.microsoft.azure.cognitiveservices.contentmoderator.TermsInList;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.*;

public class TermList {
    // NOTE: Replace this with the appropriate language for your region.
    /*
     * The language of the terms in the term lists.
     */
    private static final String lang = "eng";

    /*
     * The minimum amount of time, in milliseconds, to wait between calls
     * to the Content Moderator APIs.
     */
    private static final int throttleRate = 3000;

    /*
     * The number of minutes to delay after updating the search index before
     * performing image match operations against a the list.
     */
    private static final double latencyDelay = 0.5;

    /*
     * Creates a new term list.
     * @param client The Content Moderator client.
     * @return The term list ID.
     */
    static String CreateTermList (ContentModeratorClientImpl client) throws Exception {
        System.out.println("Creating term list.");

        BodyInner body = new BodyInner();
        body.withName("Term list name");
        body.withDescription("Term list description");
        TermListInner list = client.listManagementTermLists().create("application/json", body);
        if (list.id() == null)
        {
            throw new Exception("TermList.Id value missing.");
        }
        else {
            String list_id = list.id().toString();
            System.out.println(
                    String.format("Term list created. ID: %s.", list_id));
            Thread.sleep(throttleRate);
            return list_id;
        }
    }

    /*
     * Update the information for the indicated term list.
     * @param client The Content Moderator client.
     * @param list_id The ID of the term list to update.
     * @param name The new name for the term list.
     * @param description The new description for the term list.
     */
    static void UpdateTermList (ContentModeratorClientImpl client, String list_id, String name, String description) throws InterruptedException {
        System.out.println(
                String.format("Updating information for term list with ID %s.", list_id));
        BodyInner body = new BodyInner();
        body.withName(name);
        body.withDescription(description);
        client.listManagementTermLists().update(list_id, "application/json", body);
        Thread.sleep(throttleRate);
    }

    /*
     * Add a term to the indicated term list.
     * @param client The Content Moderator client.
     * @param list_id The ID of the term list to update.
     * @param term The term to add to the term list.
     */
    static void AddTerm (ContentModeratorClientImpl client, String list_id, String term) throws InterruptedException {
        System.out.println(
                String.format("Adding term \"%s\" to term list with ID %s.", term, list_id));
        client.listManagementTerms().addTerm(list_id, term, lang);
        Thread.sleep(throttleRate);
    }

    /*
     * Get all terms in the indicated term list.
     * @param client The Content Moderator client.
     * @param list_id The ID of the term list from which to get all terms.
     */
    static void GetAllTerms(ContentModeratorClientImpl client, String list_id) throws InterruptedException {
        System.out.println(
                String.format("Getting terms in term list with ID %s.", list_id));
        TermsInner terms = client.listManagementTerms().getAllTerms(list_id, lang);
        TermsData data = terms.data();
        for (TermsInList term : data.terms())
        {
            System.out.println(term.term());
        }
        Thread.sleep(throttleRate);
    }

    /*
     * Refresh the search index for the indicated term list.
     * @param client The Content Moderator client.
     * @param list_id The ID of the term list to refresh.
     */
    static void RefreshSearchIndex (ContentModeratorClientImpl client, String list_id) throws InterruptedException {
        System.out.println(
                String.format("Refreshing search index for term list with ID %s.", list_id));
        client.listManagementTermLists().refreshIndexMethod(list_id, lang);
        Thread.sleep((int)(latencyDelay * 60 * 1000));
    }

    /*
     * Screen the indicated text for terms in the indicated term list.
     * @param client The Content Moderator client.
     * @param list_id The ID of the term list to use to screen the text.
     * @param text The text to screen.
     */
    static void ScreenText (ContentModeratorClientImpl client, String list_id, String text) throws InterruptedException {
        System.out.println(
                String.format("Screening text: \"%s\" using term list with ID %s.", text, list_id));
        ScreenInner screen = client.textModerations().screenText(lang,
                "text/plain",
                text,
                false,
                false,
                list_id,
                false);
        if (null == screen.terms())
        {
            System.out.println("No terms from the term list were detected in the text.");
        }
        else
        {
            for (DetectedTerms term : screen.terms())
            {
                System.out.println(String.format("Found term: \"%s\" from list ID %s at index %d.", term.term(),
                        term.listId(), term.index()));
            }
        }
        Thread.sleep(throttleRate);
    }

    /*
     * Delete a term from the indicated term list.
     * @param client The Content Moderator client.
     * @param list_id The ID of the term list from which to delete the term.
     * @param term The term to delete.
     */
    static void DeleteTerm (ContentModeratorClientImpl client, String list_id, String term) throws InterruptedException {
        System.out.println(
                String.format("Removed term \"%s\" from term list with ID %s.", term, list_id));
        client.listManagementTerms().deleteTerm(list_id, term, lang);
        Thread.sleep(throttleRate);
    }

    /*
     * Delete all terms from the indicated term list.
     * @param client The Content Moderator client.
     * @param list_id The ID of the term list from which to delete all terms.
     */
    static void DeleteAllTerms (ContentModeratorClientImpl client, String list_id) throws InterruptedException {
        System.out.println(
                String.format("Removing all terms from term list with ID %s.", list_id));
        client.listManagementTerms().deleteAllTerms(list_id, lang);
        Thread.sleep(throttleRate);
    }

    /*
     * Delete the indicated term list.
     * <param name="client">The Content Moderator client.</param>
     * <param name="list_id">The ID of the term list to delete.</param>
     */
    static void DeleteTermList (ContentModeratorClientImpl client, String list_id) throws InterruptedException {
        System.out.println(
                String.format("Deleting term list with ID %s.", list_id));
        client.listManagementTermLists().delete(list_id);
        Thread.sleep(throttleRate);
    }

    static void execute(ContentModeratorClientImpl client) throws Exception {
            String list_id = CreateTermList(client);

            UpdateTermList(client, list_id, "name", "description");
            AddTerm(client, list_id, "term1");
            AddTerm(client, list_id, "term2");

            GetAllTerms(client, list_id);

            // Always remember to refresh the search index of your list
            RefreshSearchIndex(client, list_id);

            String text = "This text contains the terms \"term1\" and \"term2\".";
            ScreenText(client, list_id, text);

            DeleteTerm(client, list_id, "term1");

            // Always remember to refresh the search index of your list
            RefreshSearchIndex(client, list_id);

            text = "This text contains the terms \"term1\" and \"term2\".";
            ScreenText(client, list_id, text);

            DeleteAllTerms(client, list_id);
            DeleteTermList(client, list_id);
    }
}
