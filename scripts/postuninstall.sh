#!/bin/bash

rm /usr/bin/bahmni-batch
rm -rf /var/log/bahmni-batch

rm -rf /opt/bahmni-batch

# removing the cron job
crontab -u bahmni -l | grep -v '/usr/bin/bahmni-batch >/dev/null 2>&1'  | crontab -u bahmni -
