package com.moutamid.easyroomapp.Activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.easyroomapp.R;

public class WebViewActivity extends AppCompatActivity {
    TextView policy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        policy= (TextView) findViewById(R.id.policy);
        policy.setText(Html.fromHtml(       "<h3>Terms and Conditions:<br></h3>" +
                "<br>" +
                "<br>" +
                "<h4>1. Eligibility<br></h4>" +
                "Age Restriction: Users must be 18 years or older to use the platform.<br>" +
                "Account Responsibility: Users are responsible for maintaining the confidentiality of their login credentials and for any activity that occurs under their account.<br>" +
                "<h4>2. Acceptable Use Policy<br></h4>" +
                "No Criminal Activities: The platform must not be used for any illegal purposes, including fraud, theft, or any other criminal activity.<br>" +
                "Truthful Information: Users must provide accurate and truthful information when creating an account or posting a listing.<br>" +
                "No Harmful Behavior: Users must not harass, discriminate against, or abuse other users. This includes any unlawful, obscene, defamatory, or abusive communication or behavior.<br>" +
                "No Subletting: Only legally authorized landlords/agents may post property listings. Subletting without the owner's consent is strictly prohibited.<br>" +
                "<h4>3. User Conduct<br></h4>" +
                "Legitimate Transactions: All transactions between landlords and tenants must be legitimate and in accordance with the laws of the country.<br>" +
                "No Duplicate Listings: Users should not post duplicate or false listings.<br>" +
                "Respectful Communication: Communication between users (landlords, tenants, and support) must remain professional and courteous.<br>" +
                "<h4>4. Payments and Fees<br></h4>" +
                "Payment Gateway: All rental payments or deposits facilitated through the platform must go through secure payment gateways.<br>" +
                "<br>" +
                "<h4>5. Property Listings<br></h4>" +
                "Accuracy of Listings: Landlords/agents are responsible for the accuracy of property details, including images, rent price, and availability.<br>" +
                "No Misleading Information: Any misleading or fraudulent information on listings may lead to the removal of the post and account termination.<br>" +
                "Compliance with Local Laws: All listings must comply with local property and rental laws, including regulations around tenancy agreements and rights.<br>" +
                "<h4>6. Termination of Account<br></h4>" +
                "Right to Suspend: The platform reserves the right to suspend or terminate accounts for violations of these terms and conditions, or for any illegal or suspicious activity.<br>" +
                "User’s Right to Terminate: Users can request to terminate their account at any time by contacting support, but they will remain responsible for any outstanding payments or obligations.<br>" +
                "<h4>7. Liability Limitation<br></h4>" +
                "Platform as a Marketplace: The platform is only a facilitator for connecting landlords and tenants. It does not own, manage, or guarantee any property.<br>" +
                "No Liability for Transactions: The platform is not liable for any disputes between users, including rental agreements, payments, or personal interactions.<br>" +
                "Dispute Resolution: Any disputes between tenants and landlords must be settled between the parties. The platform is not responsible for legal or financial disputes.<br>" +
                "<h4>8. Privacy Policy<br></h4>" +
                "Data Collection: Details about how user data will be collected, stored, and used (e.g., personal identification, payment details, communication records, etc.).<br>" +
                "Data Protection: A statement ensuring that all user data will be kept secure and will not be sold or shared with third parties without consent.<br>" +
                "<br>" +
                "<h4>10. Modifications to Terms<br></h4>" +
                "Right to Change: The platform reserves the right to modify the terms and conditions at any time. Users will be notified of any significant changes.<br>" +
                "User Agreement: Continued use of the platform after changes to the terms and conditions implies acceptance of the new terms.<br>" +
                "<h4>11. Third-Party Services<br></h4>" +
                "Integration with Other Services: Users acknowledge that the app may integrate with third-party services (e.g., payment gateways, ID verification tools) and that those services have their own terms and conditions.<br>" +
                "No Liability for Third Parties: The platform is not responsible for issues arising from the use of third-party services, such as failed transactions or loss of data.<br>" +
                "<h4>12. Intellectual Property<br></h4>" +
                "Ownership of Content: The platform owns all intellectual property rights associated with its design, brand, and content.<br>" +
                "User Content: Users grant the platform a license to use any content (e.g., property listings) that they post on the platform."));
//        WebView browser = (WebView) findViewById(R.id.webview);
//        browser.getSettings().setLoadsImagesAutomatically(true);
//        browser.setWebViewClient(new MyBrowser());
////      browser.getSettings().setJavaScriptEnabled(true);
////      browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        browser.loadUrl("https://docs.google.com/document/d/10WnhJNgkaGDGTkeqyubE6nbeCbJJfJmGVkCfCp5qDKE/edit?usp=sharing");

    }

    public void backPress(View view) {
        onBackPressed();
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}