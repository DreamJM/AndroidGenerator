package com.dream.android.sample.lib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

/**
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public class IntentUtil {

    public static final String AUDIO_TYPE = "audio/*";
    public static final String VIDEO_TYPE = "video/*";
    public static final String IMAGE_TYPE = "image/*";

    /**
     * Checks whether there are applications installed which are able to handle the given action/data.
     *
     * @param context  the current context
     * @param action   the action to check
     * @param uri      that data URI to check (may be null)
     * @param mimeType the MIME type of the content (may be null)
     * @return true if there are apps which will respond to this action/data
     */
    public static boolean isIntentAvailable(Context context, String action, Uri uri, String mimeType) {
        final Intent intent = (uri != null) ? new Intent(action, uri) : new Intent(action);
        if (mimeType != null) {
            intent.setType(mimeType);
        }
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return !list.isEmpty();
    }

    /**
     * Checks whether there are applications installed which are able to handle the given action/type.
     *
     * @param context  the current context
     * @param action   the action to check
     * @param mimeType the MIME type of the content (may be null)
     * @return true if there are apps which will respond to this action/type
     */
    public static boolean isIntentAvailable(Context context, String action, String mimeType) {
        final Intent intent = new Intent(action);
        if (mimeType != null) {
            intent.setType(mimeType);
        }
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return !list.isEmpty();
    }

    /**
     * Checks whether there are applications installed which are able to handle the given intent.
     *
     * @param context the current context
     * @param intent  the intent to check
     * @return true if there are apps which will respond to this intent
     */
    public static boolean isIntentAvailable(Context context, Intent intent) {
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return !list.isEmpty();
    }

    public static class Media {
        /**
         * Open the media player to play the given media
         *
         * @param file The file path of the media to play.
         * @return the intent
         */
        public static Intent newPlayAudioFileIntent(File file) {
            return newPlayMediaFileIntent(file, AUDIO_TYPE);
        }

        /**
         * Open the media player to play the given media
         *
         * @param path The file path of the media to play.
         * @return the intent
         */
        public static Intent newPlayAudioFileIntent(String path) {
            return newPlayMediaFileIntent(path, AUDIO_TYPE);
        }

        /**
         * Open the media player to play the given media
         *
         * @param url The URL of the media to play.
         * @return the intent
         */
        public static Intent newPlayAudioIntent(String url) {
            return newPlayMediaIntent(url, AUDIO_TYPE);
        }

        /**
         * Open the media player to play the given media
         *
         * @param file The file path of the media to play.
         * @return the intent
         */
        public static Intent newPlayImageFileIntent(File file) {
            return newPlayMediaFileIntent(file, IMAGE_TYPE);
        }

        /**
         * Open the media player to play the given media
         *
         * @param path The file path of the media to play.
         * @return the intent
         */
        public static Intent newPlayImageFileIntent(String path) {
            return newPlayMediaFileIntent(path, IMAGE_TYPE);
        }

        /**
         * Open the media player to play the given media
         *
         * @param url The URL of the media to play.
         * @return the intent
         */
        public static Intent newPlayImageIntent(String url) {
            return newPlayMediaIntent(url, IMAGE_TYPE);
        }

        /**
         * Open the media player to play the given media
         *
         * @param file The file path of the media to play.
         * @return the intent
         */
        public static Intent newPlayVideoFileIntent(File file) {
            return newPlayMediaFileIntent(file, VIDEO_TYPE);
        }

        /**
         * Open the media player to play the given media
         *
         * @param path The file path of the media to play.
         * @return the intent
         */
        public static Intent newPlayVideoFileIntent(String path) {
            return newPlayMediaFileIntent(path, VIDEO_TYPE);
        }

        /**
         * Open the media player to play the given media
         *
         * @param url The URL of the media to play.
         * @return the intent
         */
        public static Intent newPlayVideoIntent(String url) {
            return newPlayMediaIntent(url, VIDEO_TYPE);
        }

        /**
         * Open the media player to play the given media
         *
         * @param url  The URL of the media to play.
         * @param type The mime type
         * @return the intent
         */
        public static Intent newPlayMediaIntent(String url, String type) {
            return newPlayMediaIntent(Uri.parse(url), type);
        }

        /**
         * Open the media player to play the given media
         *
         * @param file The file path of the media to play.
         * @param type The mime type
         * @return the intent
         */
        public static Intent newPlayMediaFileIntent(File file, String type) {
            return newPlayMediaIntent(Uri.fromFile(file), type);
        }

        /**
         * Open the media player to play the given media
         *
         * @param path The file path of the media to play.
         * @param type The mime type
         * @return the intent
         */
        public static Intent newPlayMediaFileIntent(String path, String type) {
            return newPlayMediaIntent(Uri.fromFile(new File(path)), type);
        }

        /**
         * Open the media player to play the given media Uri
         *
         * @param uri  The Uri of the media to play.
         * @param type The mime type
         * @return the intent
         */
        public static Intent newPlayMediaIntent(Uri uri, String type) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, type);
            return intent;
        }

        /**
         * Creates an intent that will launch a browser (most probably as other apps may handle specific URLs, e.g. YouTube)
         * to view the provided URL.
         *
         * @param url the URL to open
         * @return the intent
         */
        public static Intent newOpenWebBrowserIntent(String url) {
            if (!url.startsWith("https://") && !url.startsWith("http://")) {
                url = "http://" + url;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            return intent;
        }

        /**
         * Creates an intent that will launch the camera to take a picture that's saved to a temporary file so you can use
         * it directly without going through the gallery.
         *
         * @param tempFile the file that should be used to temporarily store the picture
         * @return the intent
         */
        public static Intent newTakePictureIntent(File tempFile) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            return intent;
        }

        /**
         * Creates an intent that will launch the camera to take a picture that's saved to a temporary file so you can use
         * it directly without going through the gallery.
         *
         * @param tempFile the file that should be used to temporarily store the picture
         * @return the intent
         */
        public static Intent newTakePictureIntent(String tempFile) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(tempFile)));
            return intent;
        }

        /**
         * Creates an intent that will launch the phone's picture gallery to select a picture from it.
         *
         * @return the intent
         */
        public static Intent newSelectPictureIntent() {
            Intent intent;
            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
            } else {
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            return intent;
        }
    }

    public static class Map {
        /**
         * Intent that should allow opening a map showing the given address (if it exists)
         *
         * @param address    The address to search
         * @param placeTitle The title to show on the marker
         * @return the intent
         */
        public static Intent newMapsIntent(String address, String placeTitle) {
            StringBuilder sb = new StringBuilder();
            sb.append("geo:0,0?q=");

            String addressEncoded = Uri.encode(address);
            sb.append(addressEncoded);

            // pass text for the info window
            String titleEncoded = Uri.encode(" (" + placeTitle + ")");
            sb.append(titleEncoded);

            return new Intent(Intent.ACTION_VIEW, Uri.parse(sb.toString()));
        }

        /**
         * Intent that should allow opening a map showing the given location (if it exists)
         *
         * @param latitude  The latitude of the center of the map
         * @param longitude The longitude of the center of the map
         * @return the intent
         */
        public static Intent newMapsIntent(float latitude, float longitude) {
            return newMapsIntent(latitude, longitude, null);
        }

        /**
         * Intent that should allow opening a map showing the given location (if it exists)
         *
         * @param latitude  The latitude of the center of the map
         * @param longitude The longitude of the center of the map
         * @param placeName The name to show on the marker
         * @return the intent
         */
        public static Intent newMapsIntent(float latitude, float longitude, String placeName) {
            StringBuilder sb = new StringBuilder();
            sb.append("geo:");

            sb.append(latitude);
            sb.append(",");
            sb.append(longitude);

            if (!TextUtils.isEmpty(placeName)) {
                sb.append("?q=");
                sb.append(latitude);
                sb.append(",");
                sb.append(longitude);
                sb.append("(");
                sb.append(Uri.encode(placeName));
                sb.append(")");
            }

            return new Intent(Intent.ACTION_VIEW, Uri.parse(sb.toString()));
        }


        /**
         * Intent that should allow opening a map showing the given address (if it exists)
         *
         * @param address The address to search
         * @return the intent
         */
        public static Intent newNavigationIntent(String address) {
            StringBuilder sb = new StringBuilder();
            sb.append("google.navigation:q=");

            String addressEncoded = Uri.encode(address);
            sb.append(addressEncoded);

            return new Intent(Intent.ACTION_VIEW, Uri.parse(sb.toString()));
        }

        /**
         * Intent that should allow opening a map showing the given location (if it exists)
         *
         * @param latitude  The latitude of the center of the map
         * @param longitude The longitude of the center of the map
         * @return the intent
         */
        public static Intent newNavigationIntent(float latitude, float longitude) {
            StringBuilder sb = new StringBuilder();
            sb.append("google.navigation:q=");

            sb.append(latitude);
            sb.append(",");
            sb.append(longitude);

            return new Intent(Intent.ACTION_VIEW, Uri.parse(sb.toString()));
        }

        /**
         * Opens the Street View application to the given location.
         * The URI scheme is based on the syntax used for Street View panorama information in Google Maps URLs.
         *
         * @param latitude  Latitude
         * @param longitude Longitude
         */
        public static Intent newStreetViewIntent(float latitude, float longitude) {
            return newStreetViewIntent(latitude, longitude, null, null, null, null);
        }

        /**
         * Opens the Street View application to the given location.
         * The URI scheme is based on the syntax used for Street View panorama information in Google Maps URLs.
         *
         * @param latitude  Latitude
         * @param longitude Longitude
         * @param zoom      Panorama zoom. 1.0 = normal zoom, 2.0 = zoomed in 2x, 3.0 = zoomed in 4x, and so on.
         *                  A zoom of 1.0 is 90 degree horizontal FOV for a nominal landscape mode 4 x 3 aspect ratio display Android
         *                  phones in portrait mode will adjust the zoom so that the vertical FOV is approximately the same as the
         *                  landscape vertical FOV. This means that the horizontal FOV of an Android phone in portrait mode is much
         *                  narrower than in landscape mode. This is done to minimize the fisheye lens effect that would be present
         *                  if a 90 degree horizontal FOV was used in portrait mode.
         */
        public static Intent newStreetViewIntent(float latitude, float longitude, float zoom) {
            return newStreetViewIntent(latitude, longitude, null, null, zoom, null);
        }

        /**
         * Opens the Street View application to the given location.
         * The URI scheme is based on the syntax used for Street View panorama information in Google Maps URLs.
         *
         * @param latitude  Latitude
         * @param longitude Longitude
         * @param zoom      Panorama zoom. 1.0 = normal zoom, 2.0 = zoomed in 2x, 3.0 = zoomed in 4x, and so on.
         *                  A zoom of 1.0 is 90 degree horizontal FOV for a nominal landscape mode 4 x 3 aspect ratio display Android
         *                  phones in portrait mode will adjust the zoom so that the vertical FOV is approximately the same as the
         *                  landscape vertical FOV. This means that the horizontal FOV of an Android phone in portrait mode is much
         *                  narrower than in landscape mode. This is done to minimize the fisheye lens effect that would be present
         *                  if a 90 degree horizontal FOV was used in portrait mode.
         * @param mapZoom   The map zoom of the map location associated with this panorama.
         *                  This value is passed on to the Maps activity when the Street View "Go to Maps" menu item is chosen.
         */
        public static Intent newStreetViewIntent(float latitude, float longitude, float zoom, int mapZoom) {
            return newStreetViewIntent(latitude, longitude, null, null, zoom, mapZoom);
        }

        /**
         * Opens the Street View application to the given location.
         * The URI scheme is based on the syntax used for Street View panorama information in Google Maps URLs.
         *
         * @param latitude  Latitude
         * @param longitude Longitude
         * @param yaw       Panorama center-of-view in degrees clockwise from North.
         *                  <p/>
         *                  Note: The two commas after the yaw parameter are required.
         *                  They are present for backwards-compatibility reasons.
         * @param pitch     Panorama center-of-view in degrees from -90 (look straight up) to 90 (look straight down.)
         * @param zoom      Panorama zoom. 1.0 = normal zoom, 2.0 = zoomed in 2x, 3.0 = zoomed in 4x, and so on.
         *                  A zoom of 1.0 is 90 degree horizontal FOV for a nominal landscape mode 4 x 3 aspect ratio display Android
         *                  phones in portrait mode will adjust the zoom so that the vertical FOV is approximately the same as the
         *                  landscape vertical FOV. This means that the horizontal FOV of an Android phone in portrait mode is much
         *                  narrower than in landscape mode. This is done to minimize the fisheye lens effect that would be present
         *                  if a 90 degree horizontal FOV was used in portrait mode.
         * @param mapZoom   The map zoom of the map location associated with this panorama.
         *                  This value is passed on to the Maps activity when the Street View "Go to Maps" menu item is chosen.
         */
        public static Intent newStreetViewIntent(float latitude,
                                                 float longitude,
                                                 Float yaw,
                                                 Integer pitch,
                                                 Float zoom,
                                                 Integer mapZoom) {
            StringBuilder builder = new StringBuilder("google.streetview:cbll=").append(latitude).append(",").append(longitude);

            if (yaw != null || pitch != null || zoom != null) {
                String cbpParam = String.format("%s,,%s,%s",
                        yaw == null ? "" : yaw,
                        pitch == null ? "" : pitch,
                        zoom == null ? "" : zoom);

                builder.append("&cbp=1,").append(cbpParam);
            }
            if (mapZoom != null) {
                builder.append("&mz=").append(mapZoom);
            }

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(builder.toString()));

            return intent;
        }
    }

    public static class Email {
        /**
         * Create an intent to send an email to a single recipient
         *
         * @param address The recipient address (or null if not specified)
         * @param subject The subject of the email (or null if not specified)
         * @param body    The body of the email (or null if not specified)
         * @return the intent
         */
        public static Intent newEmailIntent(String address, String subject, String body) {
            return newEmailIntent(address, subject, body, null);
        }

        /**
         * Create an intent to send an email with an attachment to a single recipient
         *
         * @param address    The recipient address (or null if not specified)
         * @param subject    The subject of the email (or null if not specified)
         * @param body       The body of the email (or null if not specified)
         * @param attachment The URI of a file to attach to the email. Note that the URI must point to a location the email
         *                   application is allowed to read and has permissions to access.
         * @return the intent
         */
        public static Intent newEmailIntent(String address, String subject, String body, Uri attachment) {
            return newEmailIntent(address == null ? null : new String[]{address}, subject, body, attachment);
        }

        /**
         * Create an intent to send an email with an attachment
         *
         * @param addresses  The recipients addresses (or null if not specified)
         * @param subject    The subject of the email (or null if not specified)
         * @param body       The body of the email (or null if not specified)
         * @param attachment The URI of a file to attach to the email. Note that the URI must point to a location the email
         *                   application is allowed to read and has permissions to access.
         * @return the intent
         */
        public static Intent newEmailIntent(String[] addresses, String subject, String body, Uri attachment) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            if (addresses != null) intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            if (body != null) intent.putExtra(Intent.EXTRA_TEXT, body);
            if (subject != null) intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            if (attachment != null) intent.putExtra(Intent.EXTRA_STREAM, attachment);
            intent.setType(MIME_TYPE_EMAIL);

            return intent;
        }

        private static final String MIME_TYPE_EMAIL = "message/rfc822";
    }

    public static class Phone {
        /**
         * Creates an intent that will allow to send an SMS without specifying the phone number
         *
         * @return the intent
         */
        public static Intent newEmptySmsIntent(Context context) {
            return newSmsIntent(context, null, (String[]) null);
        }

        /**
         * Creates an intent that will allow to send an SMS without specifying the phone number
         *
         * @param phoneNumber The phone number to send the SMS to
         * @return the intent
         */
        public static Intent newEmptySmsIntent(Context context, String phoneNumber) {
            return newSmsIntent(context, null, new String[]{phoneNumber});
        }

        /**
         * Creates an intent that will allow to send an SMS without specifying the phone number
         *
         * @param phoneNumbers The phone numbers to send the SMS to
         * @return the intent
         */
        public static Intent newEmptySmsIntent(Context context, String[] phoneNumbers) {
            return newSmsIntent(context, null, phoneNumbers);
        }

        /**
         * Creates an intent that will allow to send an SMS without specifying the phone number
         *
         * @param body The text to send
         * @return the intent
         */
        public static Intent newSmsIntent(Context context, String body) {
            return newSmsIntent(context, body, (String[]) null);
        }

        /**
         * Creates an intent that will allow to send an SMS without specifying the phone number
         *
         * @param body The text to send
         * @param phoneNumber The phone number to send the SMS to
         * @return the intent
         */
        public static Intent newSmsIntent(Context context, String body, String phoneNumber) {
            return newSmsIntent(context, body, new String[]{phoneNumber});
        }


        /**
         * Creates an intent that will allow to send an SMS to a phone number
         *
         * @param body         The text to send
         * @param phoneNumbers The phone numbers to send the SMS to (or null if you don't want to specify it)
         * @return the intent
         */
        public static Intent newSmsIntent(Context context, String body, String[] phoneNumbers) {
            Uri smsUri;
            if (phoneNumbers == null || phoneNumbers.length==0) {
                smsUri = Uri.parse("smsto:");
            } else {
                smsUri = Uri.parse("smsto:" + Uri.encode(TextUtils.join(",", phoneNumbers)));
            }

            Intent intent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent = new Intent(Intent.ACTION_SENDTO, smsUri);
                intent.setPackage(Telephony.Sms.getDefaultSmsPackage(context));
            } else {
                intent = new Intent(Intent.ACTION_VIEW, smsUri);
            }

            if (body!=null) {
                intent.putExtra("sms_body", body);
            }

            return intent;
        }

        /**
         * Creates an intent that will open the phone app and enter the given number. Unlike
         * {@link #newCallNumberIntent(String)}, this does not actually dispatch the call, so it gives the user a chance to
         * review and edit the number.
         *
         * @param phoneNumber the number to dial
         * @return the intent
         */
        public static Intent newDialNumberIntent(String phoneNumber) {
            final Intent intent;
            if (phoneNumber == null || phoneNumber.trim().length() <= 0) {
                //intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
            } else {
                //intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.replace(" ", "")));
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.replace(" ", "")));
            }
            return intent;
        }

        /**
         * Creates an intent that will immediately dispatch a call to the given number. NOTE that unlike
         * {@link #newDialNumberIntent(String)}, this intent requires the {@link android.Manifest.permission#CALL_PHONE}
         * permission to be set.
         *
         * @param phoneNumber the number to call
         * @return the intent
         */
        public static Intent newCallNumberIntent(String phoneNumber) {
            final Intent intent;
            if (phoneNumber == null || phoneNumber.trim().length() <= 0) {
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"));
            } else {
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber.replace(" ", "")));
            }
            return intent;
        }
    }

    public static class Share {
        /**
         * Creates a chooser to share some data.
         *
         * @param subject            The subject to share (might be discarded, for instance if the user picks an SMS app)
         * @param message            The message to share
         * @param chooserDialogTitle The title for the chooser dialog
         * @return the intent
         */
        public static Intent newShareTextIntent(String subject, String message, String chooserDialogTitle) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            shareIntent.setType(MIME_TYPE_TEXT);
            return Intent.createChooser(shareIntent, chooserDialogTitle);
        }

        private static final String MIME_TYPE_TEXT = "text/*";
    }

    public static class System {
        /**
         * Intent that should open the app store of the device on the current application page
         *
         * @param context The context associated to the application
         * @return the intent
         */
        public static Intent newMarketForAppIntent(Context context) {
            String packageName = context.getApplicationContext().getPackageName();
            return newMarketForAppIntent(context, packageName);
        }

        /**
         * Intent that should open the app store of the device on the given application
         *
         * @param context     The context associated to the application
         * @param packageName The package name of the application to find on the market
         * @return the intent or null if no market is available for the intent
         */
        public static Intent newMarketForAppIntent(Context context, String packageName) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));

            if (!isIntentAvailable(context, intent)) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("amzn://apps/android?p=" + packageName));
            }

            if (!isIntentAvailable(context, intent)) {
                intent = null;
            }

            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            }

            return intent;
        }

        /**
         * Intent that should open either the Google Play app or if not available, the web browser on the Google Play website
         *
         * @param context     The context associated to the application
         * @param packageName The package name of the application to find on the market
         * @return the intent for native application or an intent to redirect to the browser if google play is not installed
         */
        public static Intent newGooglePlayIntent(Context context, String packageName) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));

            if (!isIntentAvailable(context, intent)) {
                intent = Media.newOpenWebBrowserIntent("https://play.google.com/store/apps/details?id="
                        + packageName);
            }

            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            }

            return intent;
        }

        /**
         * Intent that should open either the Amazon store app or if not available, the web browser on the Amazon website
         *
         * @param context     The context associated to the application
         * @param packageName The package name of the application to find on the market
         * @return the intent for native application or an intent to redirect to the browser if google play is not installed
         */
        public static Intent newAmazonStoreIntent(Context context, String packageName) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("amzn://apps/android?p=" + packageName));

            if (!isIntentAvailable(context, intent)) {
                intent = Media.newOpenWebBrowserIntent("http://www.amazon.com/gp/mas/dl/android?p="
                        + packageName);
            }

            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            }

            return intent;
        }

        /**
         * Pick file from sdcard with file manager. Chosen file can be obtained from Intent in onActivityResult.
         * See code below for example:
         * <p/>
         * <pre><code>
         *     @Override
         *     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         *         Uri file = data.getData();
         *     }
         * </code></pre>
         */
        public static Intent newPickFileIntent() {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("file/*");
            return intent;
        }
    }

}
