package org.briarproject.masterproject.android.settings;

import static org.briarproject.bramble.api.plugin.TorConstants.PREF_TOR_NETWORK_AUTOMATIC;
import static org.briarproject.masterproject.android.util.UiUtils.getCountryDisplayName;

import android.content.Context;

import androidx.preference.ListPreference;
import androidx.preference.Preference.SummaryProvider;

import org.briarproject.bramble.api.system.LocationUtils;
import org.briarproject.bramble.plugin.tor.CircumventionProvider;
import org.briarproject.briar.R;
import org.briarproject.nullsafety.NotNullByDefault;

@NotNullByDefault
class TorSummaryProvider implements SummaryProvider<ListPreference> {

    private final Context ctx;
    private final LocationUtils locationUtils;
    private final CircumventionProvider circumventionProvider;

    TorSummaryProvider(Context ctx,
                       LocationUtils locationUtils,
                       CircumventionProvider circumventionProvider) {
        this.ctx = ctx;
        this.locationUtils = locationUtils;
        this.circumventionProvider = circumventionProvider;
    }

    @Override
    public CharSequence provideSummary(ListPreference preference) {
        int torNetworkSetting = Integer.parseInt(preference.getValue());

        if (torNetworkSetting != PREF_TOR_NETWORK_AUTOMATIC) {
            return preference.getEntry();  // use setting value
        }

        // Look up country name in the user's chosen language if available
        String country = locationUtils.getCurrentCountry();
        String countryName = getCountryDisplayName(country);

        boolean blocked =
                circumventionProvider.isTorProbablyBlocked(country);
        boolean useBridges = circumventionProvider.doBridgesWork(country);
        String setting =
                ctx.getString(R.string.tor_network_setting_without_bridges);
        if (blocked && useBridges) {
            setting = ctx.getString(R.string.tor_network_setting_with_bridges);
        } else if (blocked) {
            setting = ctx.getString(R.string.tor_network_setting_never);
        }
        return ctx.getString(R.string.tor_network_setting_summary, setting,
                countryName);
    }

}
