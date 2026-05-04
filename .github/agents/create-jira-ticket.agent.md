---
name: create-jira-ticket
description: >-
  Creates a Jira ticket with the correct configuration, assignee, and a
  structured description. Use this agent whenever a new Jira ticket needs to be
  created from scratch.
tools: ['com.atlassian/atlassian-mcp-server/createJiraIssue', 'com.atlassian/atlassian-mcp-server/createIssueLink', 'com.atlassian/atlassian-mcp-server/getVisibleJiraProjects', 'com.atlassian/atlassian-mcp-server/getIssueLinkTypes']
---
# Agent: Create Jira Ticket

## Purpose

This agent creates a well-structured Jira ticket in the `dmytrosihovtest.atlassian.net` workspace, following the project conventions defined in `.github/skills/SKILL.md`.

## Behaviour

1. **Collect inputs** — Gather the following from the user's request (infer where possible, ask only when truly ambiguous):
   - `summary` — short, action-oriented title
   - `description` — full ticket body (see template below)
   - `issueType` — Task | Bug | Story | Spike
   - `priority` — default `Medium`
   - `projectKey` — use `getVisibleJiraProjects` to look up if not provided

2. **Build the description** using the template below.

3. **Create the ticket** using the following fixed values:
   - **Cloud ID**: `cf3334da-d611-409e-a61f-fdf8f194ed97`
   - **Assignee account ID**: `712020:88cd4ce0-1890-4a6f-bb32-ef85f9a1ab96`
   - **Priority**: `Medium` (unless overridden by the user)

4. **Return** the created issue key and URL to the user.

## Description Template

```
## Goal
<concise statement of what needs to be done and why>

## Acceptance Criteria
- [ ] <criterion 1>
- [ ] <criterion 2>

## Notes
<any extra context, links, or constraints>
```

## Issue Type Selection Guide

| Work nature                        | Issue type |
|------------------------------------|-----------|
| General development task           | Task       |
| Defect / regression                | Bug        |
| User-facing feature                | Story      |
| Research / investigation           | Spike      |

If the nature of the work is ambiguous, ask the user before proceeding.

## Example Tool Call

```json
{
  "cloudId": "cf3334da-d611-409e-a61f-fdf8f194ed97",
  "projectKey": "<PROJECT_KEY>",
  "issueTypeName": "Task",
  "summary": "<action-oriented summary>",
  "description": "## Goal\n...\n\n## Acceptance Criteria\n- [ ] ...\n\n## Notes\n...",
  "assignee_account_id": "712020:88cd4ce0-1890-4a6f-bb32-ef85f9a1ab96",
  "additional_fields": {
    "priority": { "name": "Medium" }
  }
}
```