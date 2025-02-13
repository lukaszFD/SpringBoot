UI Needs Analysis for the Vault Data Consolidation Application

1. Application Overview

The application consolidates data from access management systems such as OneVault and HashiCorp Vault, as well as platforms like Unix, Windows, SQL, and PostgreSQL. It generates an account reconciliation report stored in an Oracle 19c database. Currently, the UI presents a full report with all accounts, which is a security risk since every user can see all accounts, regardless of their role.

Additionally, the current UI lacks role-based access control (RBAC), preventing customization of what each user can view or edit. The application is in the development (DEV) environment, and the initial UI version is incomplete, requiring significant modifications.


---

UI Requirements

1. Restricting Account Visibility

Current State:

The UI displays a full reconciliation report to all users.

Any user can see all accounts, even those they should not have access to.

There is no distinction between user roles in terms of visibility.


Required Changes:

The UI should only display accounts belonging to the logged-in user.

Managers should be able to view the accounts of their subordinates.

Administrators should retain full visibility but in a separate section.

Implement an efficient filtering mechanism to limit data exposure based on roles.


Security Benefit: Restricting visibility ensures that users can only access relevant data, preventing unauthorized exposure of sensitive account information.


---

2. Limiting Editable Fields

Current State:

The report does not allow users to edit any fields.

The database does not store fields such as "description" or "comments."

The daily report generation process replaces all data, meaning any modifications would be lost.


Required Changes:

Users should be able to modify specific fields: description and comments.

These fields must be added to the database and linked to the respective accounts.

A merge mechanism should be implemented to retain user modifications while updating the report data.

All changes should be audited, tracking who modified what and when.


Security & Usability Benefit: Providing editable fields enhances user experience while maintaining control over data integrity.


---

3. Implementing Role-Based Access Control (RBAC)

Current State:

All users see the same data without restrictions.

There is no differentiation between standard users, managers, and administrators.

The database does not have a role structure.


Required Changes:

Implement three user roles:

1. Standard User (USER) – Sees only their own accounts and can edit description/comments.


2. Manager (MANAGER) – Sees their accounts plus accounts of their subordinates.


3. Administrator (ADMIN) – Full access, including detailed reconciliation reports and system logs.



Roles must be stored in the database and assigned to user IDs.

The UI should restrict access to reports based on user roles.


Security Benefit: Implementing RBAC ensures that only authorized personnel access sensitive reports and data.


---

4. Administrator-Only Section – ETL Logs & Backend Reports

Current State:

There is no dedicated section for administrators.

No visibility into ETL processing logs, input files, or backend operations.


Required Changes:

A new administrator-only tab should be added to the UI.

This section should contain:

ETL process reports (summary of loaded input files).

Backend logs (application/database logs).


Access to this tab should be restricted to ADMIN users only.


Security & Operational Benefit: Providing ETL reports helps administrators monitor data integrity and troubleshoot issues.


---

5. Approval Process for Non-Compliant Accounts

Current State:

Users cannot take direct action to fix non-compliant accounts.

No approval process is in place for account remediation.

No integration with MyIT for formal request tracking.


Required Changes:

Introduce a "Non-Compliant Accounts" tab in the UI.

This tab should list accounts that do not meet compliance requirements.

Users should be able to select an account and initiate a remediation request.

The system should provide an option to:

Propose data corrections via API.

Submit a formal request in MyIT for managerial approval.


Managers and administrators should have an approval workflow to review and accept/reject changes.

Audit logs should capture who initiated, approved, or rejected remediation actions.


Security & Compliance Benefit: This feature ensures that changes to non-compliant accounts are controlled, reviewed, and documented, reducing security risks and improving compliance tracking.


---

Database & API Changes Required

Database Modifications:

Add role management tables to assign user roles.

Create new fields (description, comments) in the account reconciliation table.

Implement a merge strategy to retain editable fields while refreshing report data.

Introduce an audit table to track modifications to user-editable fields.

Create a non-compliant accounts table to store remediation requests and approval statuses.


API Changes:

Modify existing API to filter data based on the logged-in user’s role.

Create new endpoints for:

Fetching accounts based on user permissions.

Storing and retrieving editable fields.

Retrieving ETL process logs (for administrators).

Submitting remediation requests and retrieving their status.

Approving/rejecting remediation actions by managers/admins.


Implement security controls to validate user roles on every API request.



---

Summary of Required UI Changes

These improvements will enhance security, improve usability, and ensure compliance with data protection requirements.

