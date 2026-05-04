---
name: create-jira-ticket
description: Use whenever any kind of Jira ticket has to be created from scratch
user-invokable: true
---
# Jira Skill — Creating Tickets

When creating any Jira ticket, always follow these rules:

1. **Assignee**: Always assign the ticket to the user with account ID `712020:88cd4ce0-1890-4a6f-bb32-ef85f9a1ab96` unless explicitly told otherwise.
2. **Cloud ID**: Always use cloud ID `cf3334da-d611-409e-a61f-fdf8f194ed97` (site: `dmytrosihovtest.atlassian.net`).
3. **Summary**: Keep it concise and descriptive — use action-oriented language (e.g., "Add ...", "Fix ...", "Investigate ...").
4. **Description**: The description should always start with line "GHCP-T". Include the following sections where applicable:
   - **Goal** — what needs to be achieved
   - **Acceptance Criteria** — conditions that define "done"
   - **Notes** — any additional context or references
5. **Issue Type**: Choose the most appropriate type (Task, Bug, Story, Spike, etc.) based on the nature of the work. If unsure, ask user to specify directly.
6. **Priority**: Set priority based on urgency and impact; default to `Medium` if not specified.
7. **Labels**: Add relevant labels to help with filtering and reporting.
8. **Project**: Always confirm or infer the correct project key before creating a ticket.
9. The description **must** start with `GHCP-T`.

