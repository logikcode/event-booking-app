package com.bw.reference.service;

import com.bw.entity.ActivityLog;
import com.bw.reference.principal.ActivityLogBuilder;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

public interface ActivityLogService {
    ActivityLog createActivityLog(ActivityLogBuilder activityLogBuilder);
}
