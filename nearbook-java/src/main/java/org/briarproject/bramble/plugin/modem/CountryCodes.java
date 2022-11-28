package org.briarproject.bramble.plugin.modem;

import java.util.HashMap;
import java.util.Map;

class CountryCodes {

    private static final Country[] COUNTRIES = {
            new Country("AD", "Andorra", "376", "00", ""),
            new Country("AE", "United Arab Emirates", "971", "00", "0"),
            new Country("AF", "Afghanistan", "93", "00", "0"),
            new Country("AG", "Antigua and Barbuda", "1", "011", "1"),
            new Country("AI", "Anguilla", "1", "011", "1"),
            new Country("AL", "Albania", "355", "00", "0"),
            new Country("AM", "Armenia", "374", "00", "8"),
            new Country("AN", "Netherlands Antilles", "599", "00", "0"),
            new Country("AO", "Angola", "244", "00", "0"),
            new Country("AQ", "Antarctica", "672", "", ""),
            new Country("AR", "Argentina", "54", "00", "0"),
            new Country("AS", "American Samoa", "1", "011", "1"),
            new Country("AT", "Austria", "43", "00", "0"),
            new Country("AU", "Australia", "61", "0011", "0"),
            new Country("AW", "Aruba", "297", "00", ""),
            new Country("AZ", "Azerbaijan", "994", "00", "8"),
            new Country("BA", "Bosnia and Herzegovina", "387", "00", "0"),
            new Country("BB", "Barbados", "1", "011", "1"),
            new Country("BD", "Bangladesh", "880", "00", "0"),
            new Country("BE", "Belgium", "32", "00", "0"),
            new Country("BF", "Burkina Faso", "226", "00", ""),
            new Country("BG", "Bulgaria", "359", "00", "0"),
            new Country("BH", "Bahrain", "973", "00", ""),
            new Country("BI", "Burundi", "257", "00", ""),
            new Country("BJ", "Benin", "229", "00", ""),
            new Country("BM", "Bermuda", "1", "011", "1"),
            new Country("BN", "Brunei Darussalam", "673", "00", "0"),
            new Country("BO", "Bolivia", "591", "00", "0"),
            new Country("BR", "Brazil", "55", "00", "0"),
            new Country("BS", "Bahamas", "1", "011", "1"),
            new Country("BT", "Bhutan", "975", "00", ""),
            new Country("BV", "Bouvet Island (Norway)", "47", "00", ""),
            new Country("BW", "Botswana", "267", "00", ""),
            new Country("BY", "Belarus", "375", "8**10", "8"),
            new Country("BZ", "Belize", "501", "00", "0"),
            new Country("CA", "Canada", "1", "011", "1"),
            new Country("CC", "Cocos (Keeling) Islands", "61", "0011", "0"),
            new Country("CD", "Congo (Republic)", "243", "00", ""),
            new Country("CF", "Central African Republic", "236", "00", ""),
            new Country("CG", "Congo (Democratic Republic)", "242", "00", "0"),
            new Country("CH", "Switzerland", "41", "00", "0"),
            new Country("CI", "Cote D'Ivoire", "225", "00", "0"),
            new Country("CK", "Cook Islands", "682", "00", "00"),
            new Country("CL", "Chile", "56", "00", "0"),
            new Country("CM", "Cameroon", "237", "00", ""),
            new Country("CN", "China", "86", "00", "0"),
            new Country("CO", "Colombia", "57", "009", "09"),
            new Country("CR", "Costa Rica", "506", "00", ""),
            new Country("CU", "Cuba", "53", "119", "0"),
            new Country("CV", "Cape Verde Islands", "238", "0", ""),
            new Country("CX", "Christmas Island", "61", "0011", "0"),
            new Country("CY", "Cyprus", "357", "00", ""),
            new Country("CZ", "Czech Republic", "420", "00", ""),
            new Country("DE", "Germany", "49", "00", "0"),
            new Country("DJ", "Djibouti", "253", "00", ""),
            new Country("DK", "Denmark", "45", "00", ""),
            new Country("DM", "Dominica", "1", "011", "1"),
            new Country("DO", "Dominican Republic", "1", "011", "1"),
            new Country("DZ", "Algeria", "213", "00", "7"),
            new Country("EC", "Ecuador", "593", "00", "0"),
            new Country("EE", "Estonia", "372", "00", ""),
            new Country("EG", "Egypt", "20", "00", "0"),
            new Country("EH", "Western Sahara", "212", "00", "0"),
            new Country("ER", "Eritrea", "291", "00", "0"),
            new Country("ES", "Spain", "34", "00", ""),
            new Country("ET", "Ethiopia", "251", "00", "0"),
            new Country("FI", "Finland", "358", "00", "0"),
            new Country("FJ", "Fiji", "679", "00", ""),
            new Country("FK", "Falkland Islands (Malvinas)", "500", "00", ""),
            new Country("FM", "Micronesia", "691", "011", "1"),
            new Country("FO", "Faroe Islands", "298", "00", ""),
            new Country("FR", "France", "33", "00", ""),
            new Country("GA", "Gabonese Republic", "241", "00", ""),
            new Country("GB", "United Kingdom", "44", "00", "0"),
            new Country("GD", "Grenada", "1", "011", "4"),
            new Country("GE", "Georgia", "995", "8**10", "8"),
            new Country("GF", "French Guiana", "594", "00", ""),
            new Country("GH", "Ghana", "233", "00", ""),
            new Country("GI", "Gibraltar", "350", "00", ""),
            new Country("GL", "Greenland", "299", "00", ""),
            new Country("GM", "Gambia", "220", "00", ""),
            new Country("GN", "Guinea", "224", "00", "0"),
            new Country("GP", "Guadeloupe", "590", "00", ""),
            new Country("GQ", "Equatorial Guinea", "240", "00", ""),
            new Country("GR", "Greece", "30", "00", ""),
            new Country("GS", "South Georgia and the South Sandwich Islands", "995", "8**10", "8"),
            new Country("GT", "Guatemala", "502", "00", ""),
            new Country("GU", "Guam", "1", "011", "1"),
            new Country("GW", "Guinea-Bissau", "245", "00", ""),
            new Country("GY", "Guyana", "592", "001", "0"),
            new Country("HK", "Hong Kong", "852", "001", ""),
            new Country("HM", "Heard Island and McDonald Islands", "692", "00", "0"),
            new Country("HN", "Honduras", "504", "00", "0"),
            new Country("HR", "Croatia", "385", "00", "0"),
            new Country("HT", "Haiti", "509", "00", "0"),
            new Country("HU", "Hungary", "36", "00", "06"),
            new Country("ID", "Indonesia", "62", "001", "0"),
            new Country("IE", "Ireland", "353", "00", "0"),
            new Country("IL", "Israel", "972", "00", "0"),
            new Country("IN", "India", "91", "00", "0"),
            new Country("IO", "British Indian Ocean Territory", "246", "00", ""),
            new Country("IQ", "Iraq", "964", "00", "0"),
            new Country("IR", "Iran", "98", "00", "0"),
            new Country("IS", "Iceland", "354", "00", "0"),
            new Country("IT", "Italy", "39", "00", ""),
            new Country("JM", "Jamaica", "1", "011", "1"),
            new Country("JO", "Jordan", "962", "00", "0"),
            new Country("JP", "Japan", "81", "001", "0"),
            new Country("KE", "Kenya", "254", "000", "0"),
            new Country("KG", "Kyrgyzstan", "996", "00", "0"),
            new Country("KH", "Cambodia", "855", "001", "0"),
            new Country("KI", "Kiribati", "686", "00", "0"),
            new Country("KM", "Comoros", "269", "00", ""),
            new Country("KN", "Saint Kitts and Nevis", "1", "011", "1"),
            new Country("KP", "Korea (North)", "850", "00", "0"),
            new Country("KR", "Korea (South)", "82", "001", "0"),
            new Country("KW", "Kuwait", "965", "00", "0"),
            new Country("KY", "Cayman Islands", "1", "011", "1"),
            new Country("KZ", "Kazakhstan", "7", "8**10", "8"),
            new Country("LA", "Laos", "856", "00", "0"),
            new Country("LB", "Lebanon", "961", "00", "0"),
            new Country("LC", "Saint Lucia", "1", "011", "1"),
            new Country("LI", "Liechtenstein", "423", "00", ""),
            new Country("LK", "Sri Lanka", "94", "00", "0"),
            new Country("LR", "Liberia", "231", "00", "22"),
            new Country("LS", "Lesotho", "266", "00", "0"),
            new Country("LT", "Lithuania", "370", "00", "8"),
            new Country("LU", "Luxembourg", "352", "00", ""),
            new Country("LV", "Latvia", "371", "00", "8"),
            new Country("LY", "Libya", "218", "00", "0"),
            new Country("MA", "Morocco", "212", "00", ""),
            new Country("MC", "Monaco", "377", "00", "0"),
            new Country("MD", "Moldova", "373", "00", "0"),
            new Country("ME", "Montenegro", "382", "99", "0"),
            new Country("MG", "Madagascar", "261", "00", "0"),
            new Country("MH", "Marshall Islands", "692", "011", "1"),
            new Country("MK", "Macedonia", "389", "00", "0"),
            new Country("ML", "Mali", "223", "00", "0"),
            new Country("MM", "Myanmar", "95", "00", ""),
            new Country("MN", "Mongolia", "976", "001", "0"),
            new Country("MO", "Macao", "853", "00", "0"),
            new Country("MP", "Northern Mariana Islands", "1", "011", "1"),
            new Country("MQ", "Martinique", "596", "00", "0"),
            new Country("MR", "Mauritania", "222", "00", "0"),
            new Country("MS", "Montserrat", "1", "011", "1"),
            new Country("MT", "Malta", "356", "00", "21"),
            new Country("MU", "Mauritius", "230", "00", "0"),
            new Country("MV", "Maldives", "960", "00", "0"),
            new Country("MW", "Malawi", "265", "00", ""),
            new Country("MX", "Mexico", "52", "00", "01"),
            new Country("MY", "Malaysia", "60", "00", "0"),
            new Country("MZ", "Mozambique", "258", "00", "0"),
            new Country("NA", "Namibia", "264", "00", "0"),
            new Country("NC", "New Caledonia", "687", "00", "0"),
            new Country("NE", "Niger", "227", "00", "0"),
            new Country("NF", "Norfolk Island", "672", "00", ""),
            new Country("NG", "Nigeria", "234", "009", "0"),
            new Country("NI", "Nicaragua", "505", "00", "0"),
            new Country("NL", "Netherlands", "31", "00", "0"),
            new Country("NO", "Norway", "47", "00", ""),
            new Country("NP", "Nepal", "977", "00", "0"),
            new Country("NR", "Nauru", "674", "00", "0"),
            new Country("NU", "Niue", "683", "00", "0"),
            new Country("NZ", "New Zealand", "64", "00", "0"),
            new Country("OM", "Oman", "968", "00", "0"),
            new Country("PA", "Panama", "507", "00", "0"),
            new Country("PE", "Peru", "51", "00", "0"),
            new Country("PF", "French Polynesia", "689", "00", ""),
            new Country("PG", "Papua New Guinea", "675", "05", ""),
            new Country("PH", "Philippines", "63", "00", "0"),
            new Country("PK", "Pakistan", "92", "00", "0"),
            new Country("PL", "Poland", "48", "00", "0"),
            new Country("PM", "Saint Pierre and Miquelon", "508", "00", "0"),
            new Country("PN", "Pitcairn", "872", "", ""),
            new Country("PR", "Puerto Rico", "1", "011", "1"),
            new Country("PS", "Palestine", "970", "00", "0"),
            new Country("PT", "Portugal", "351", "00", ""),
            new Country("PW", "Palau", "680", "011", ""),
            new Country("PY", "Paraguay", "595", "002", "0"),
            new Country("QA", "Qatar", "974", "00", "0"),
            new Country("RE", "Reunion", "262", "00", "0"),
            new Country("RO", "Romania", "40", "00", "0"),
            new Country("RS", "Serbia", "381", "99", "0"),
            new Country("RU", "Russia", "7", "8**10", "8"),
            new Country("RW", "Rwanda", "250", "00", "0"),
            new Country("SA", "Saudi Arabia", "966", "00", "0"),
            new Country("SB", "Solomon Islands", "677", "00", ""),
            new Country("SC", "Seychelles", "248", "00", "0"),
            new Country("SD", "Sudan", "249", "00", "0"),
            new Country("SE", "Sweden", "46", "00", "0"),
            new Country("SG", "Singapore", "65", "001", ""),
            new Country("SH", "Saint Helena", "290", "00", ""),
            new Country("SI", "Slovenia", "386", "00", "0"),
            new Country("SJ", "Svalbard and Jan Mayen", "378", "00", "0"),
            new Country("SK", "Slovakia", "421", "00", "0"),
            new Country("SL", "Sierra Leone", "232", "00", "0"),
            new Country("SM", "San Marino", "378", "00", "0"),
            new Country("SN", "Senegal", "221", "00", "0"),
            new Country("SO", "Somalia", "252", "00", ""),
            new Country("SR", "Suriname", "597", "00", ""),
            new Country("ST", "Sao Tome and Principe", "239", "00", "0"),
            new Country("SV", "El Salvador", "503", "00", ""),
            new Country("SY", "Syria", "963", "00", "0"),
            new Country("SZ", "Swaziland", "268", "00", ""),
            new Country("TC", "Turks and Caicos Islands", "1", "011", "1"),
            new Country("TD", "Chad", "235", "15", ""),
            new Country("TF", "French Southern Territories", "596", "00", "0"),
            new Country("TG", "Togo", "228", "00", ""),
            new Country("TH", "Thailand", "66", "001", "0"),
            new Country("TJ", "Tajikistan", "992", "8**10", "8"),
            new Country("TK", "Tokelau", "690", "00", ""),
            new Country("TL", "Timor-Leste", "670", "00", ""),
            new Country("TM", "Turkmenistan", "993", "8**10", "8"),
            new Country("TN", "Tunisia", "216", "00", "0"),
            new Country("TO", "Tonga Islands", "676", "00", ""),
            new Country("TR", "Turkey", "90", "00", "0"),
            new Country("TT", "Trinidad and Tobago", "1", "011", "1"),
            new Country("TV", "Tuvalu", "688", "00", ""),
            new Country("TW", "Taiwan", "886", "002", ""),
            new Country("TZ", "Tanzania", "255", "000", "0"),
            new Country("UA", "Ukraine", "380", "8**10", "8"),
            new Country("UG", "Uganda", "256", "000", "0"),
            new Country("US", "United States", "1", "011", "1"),
            new Country("UY", "Uruguay", "598", "00", "0"),
            new Country("UZ", "Uzbekistan", "998", "8**10", "8"),
            new Country("VA", "Holy See (Vatican City State)", "379", "00", ""),
            new Country("VC", "Saint Vincent and the Grenadines", "1", "011", "1"),
            new Country("VE", "Venezuela", "58", "00", "0"),
            new Country("VG", "Virgin Islands (British)", "1", "011", "1"),
            new Country("VI", "Virgin Islands (U.S.)", "1", "011", "1"),
            new Country("VN", "Viet Nam", "84", "00", "0"),
            new Country("VU", "Vanuatu", "678", "00", ""),
            new Country("WF", "Wallis and Futuna Islands", "681", "19", ""),
            new Country("WS", "Samoa (Western)", "685", "0", "0"),
            new Country("YE", "Yemen", "967", "00", "0"),
            new Country("YT", "Mayotte", "269", "00", ""),
            new Country("ZA", "South Africa", "27", "09", "0"),
            new Country("ZM", "Zambia", "260", "00", "0"),
            new Country("ZW", "Zimbabwe", "263", "110", "0")
    };

    private static final Map<String, Country> COUNTRY_MAP = new HashMap<>();

    static {
        for (Country c : COUNTRIES) COUNTRY_MAP.put(c.iso3166, c);
    }

    static String translate(String number, String callerIso, String calleeIso) {
        Country from = COUNTRY_MAP.get(callerIso);
        Country to = COUNTRY_MAP.get(calleeIso);
        if (from == null || to == null) return null;
        // Strip any prefixes and country codes from the number
        String plusCountryCode = "+" + to.countryCode;
        String iddCountryCode = to.idd + to.countryCode;
        if (number.startsWith(plusCountryCode))
            number = number.substring(plusCountryCode.length());
        else if (number.startsWith(iddCountryCode))
            number = number.substring(iddCountryCode.length());
        else if (number.startsWith(to.ndd))
            number = number.substring(to.ndd.length());
        if (from == to) return from.ndd + number; // National
        return from.idd + to.countryCode + number; // International
    }

    private static class Country {

        private final String iso3166, countryCode, idd, ndd;

        private Country(String iso3166, String englishName, String countryCode,
                        String idd, String ndd) {
            this.iso3166 = iso3166;
            this.countryCode = countryCode;
            this.idd = idd;
            this.ndd = ndd;
        }
    }
}