package com.okstatelibrary.spacesui.util;

/**
 * Represents a static ribbon message to be displayed on a user interface, typically used for system notifications
 * such as maintenance alerts, important announcements, or general status messages.
 *
 * <p>This class holds two static fields:
 * <ul>
 *   <li>{@code messageType} – Specifies the type of message (e.g., "info", "warning", "error").</li>
 *   <li>{@code message} – The actual message content to be displayed.</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>
 * RibbonMessage.messageType = "info";
 * RibbonMessage.message = "Scheduled Maintenance - https://spaces.library.okstate.edu/ will be undergoing "
 *       + "scheduled maintenance on 09/15/2023 at 12:00 a.m. for approximately 10 minutes.";
 * </pre>
 *
 * <p>Note: As all fields are static, this class acts as a global holder and does not require instantiation.</p>
 */
public class RibbonMessage {

    /**
     * The type of the ribbon message (e.g., "info", "warning", "error").
     * This can be used by the front-end to determine styling or iconography.
     */
    public static String messageType;

    /**
     * The content of the ribbon message to be displayed to users.
     * This may include system alerts, maintenance notices, or other announcements.
     */
    public static String message;
}