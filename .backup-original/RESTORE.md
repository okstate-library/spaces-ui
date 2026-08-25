# Original file backups

Snapshot of the files taken **before** the 2026-08-24 button / breadcrumb / banner
title-case changes. These are byte-for-byte copies of the originals.

Files backed up:

- `src/main/resources/static/css/template-2026.css`
- `src/main/resources/static/js/main.js`
- `src/main/resources/templates/pages/summary.html`
- `src/main/resources/templates/pages/booking.html`
- `src/main/resources/templates/pages/sessions.html`

## To undo all the changes

From the repo root (`C:\Github_Projects\spaces-ui`):

```bash
cp -r .backup-original/src/. src/
```

That copies every backed-up file back over its working copy. Nothing else in the
repo is touched.
