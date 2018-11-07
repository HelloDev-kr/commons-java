package kr.hellodev.support.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 07/11/2018 10:51 AM
 */
public class StringUtil {

    private static final String FROM_CDATA = "<![CDATA[";
    private static final String TO_CDATA = "]]>";

    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
    private static StringBuffer HANGUL_2350_STRINGBUFFER = new StringBuffer();
    private static final int KOREAN_BYTE_LENGTH = "가".getBytes().length;
    private final static char WHITE_SPACE = ' ';
    /**
     * The maximum size to which the padding constant(s) can expand.
     */
    private static final int PAD_LIMIT = 8192;
    private static final char[] HANGUL_2350 = {
            0xAC00,
            0xAC01,
            0xAC04,
            0xAC07,
            0xAC08,
            0xAC09,
            0xAC0A,
            0xAC10,
            0xAC11,
            0xAC12,
            0xAC13,
            0xAC14,
            0xAC15,
            0xAC16,
            0xAC17,
            0xAC19,
            0xAC1A,
            0xAC1B,
            0xAC1C,
            0xAC1D,
            0xAC20,
            0xAC24,
            0xAC2C,
            0xAC2D,
            0xAC2F,
            0xAC30,
            0xAC31,
            0xAC38,
            0xAC39,
            0xAC3C,
            0xAC40,
            0xAC4B,
            0xAC4D,
            0xAC54,
            0xAC58,
            0xAC5C,
            0xAC70,
            0xAC71,
            0xAC74,
            0xAC77,
            0xAC78,
            0xAC7A,
            0xAC80,
            0xAC81,
            0xAC83,
            0xAC84,
            0xAC85,
            0xAC86,
            0xAC89,
            0xAC8A,
            0xAC8B,
            0xAC8C,
            0xAC90,
            0xAC94,
            0xAC9C,
            0xAC9D,
            0xAC9F,
            0xACA0,
            0xACA1,
            0xACA8,
            0xACA9,
            0xACAA,
            0xACAC,
            0xACAF,
            0xACB0,
            0xACB8,
            0xACB9,
            0xACBB,
            0xACBC,
            0xACBD,
            0xACC1,
            0xACC4,
            0xACC8,
            0xACCC,
            0xACD5,
            0xACD7,
            0xACE0,
            0xACE1,
            0xACE4,
            0xACE7,
            0xACE8,
            0xACEA,
            0xACEC,
            0xACEF,
            0xACF0,
            0xACF1,
            0xACF3,
            0xACF5,
            0xACF6,
            0xACFC,
            0xACFD,
            0xAD00,
            0xAD04,
            0xAD06,
            0xAD0C,
            0xAD0D,
            0xAD0F,
            0xAD11,
            0xAD18,
            0xAD1C,
            0xAD20,
            0xAD29,
            0xAD2C,
            0xAD2D,
            0xAD34,
            0xAD35,
            0xAD38,
            0xAD3C,
            0xAD44,
            0xAD45,
            0xAD47,
            0xAD49,
            0xAD50,
            0xAD54,
            0xAD58,
            0xAD61,
            0xAD63,
            0xAD6C,
            0xAD6D,
            0xAD70,
            0xAD73,
            0xAD74,
            0xAD75,
            0xAD76,
            0xAD7B,
            0xAD7C,
            0xAD7D,
            0xAD7F,
            0xAD81,
            0xAD82,
            0xAD88,
            0xAD89,
            0xAD8C,
            0xAD90,
            0xAD9C,
            0xAD9D,
            0xADA4,
            0xADB7,
            0xADC0,
            0xADC1,
            0xADC4,
            0xADC8,
            0xADD0,
            0xADD1,
            0xADD3,
            0xADDC,
            0xADE0,
            0xADE4,
            0xADF8,
            0xADF9,
            0xADFC,
            0xADFF,
            0xAE00,
            0xAE01,
            0xAE08,
            0xAE09,
            0xAE0B,
            0xAE0D,
            0xAE14,
            0xAE30,
            0xAE31,
            0xAE34,
            0xAE37,
            0xAE38,
            0xAE3A,
            0xAE40,
            0xAE41,
            0xAE43,
            0xAE45,
            0xAE46,
            0xAE4A,
            0xAE4C,
            0xAE4D,
            0xAE4E,
            0xAE50,
            0xAE54,
            0xAE56,
            0xAE5C,
            0xAE5D,
            0xAE5F,
            0xAE60,
            0xAE61,
            0xAE65,
            0xAE68,
            0xAE69,
            0xAE6C,
            0xAE70,
            0xAE78,
            0xAE79,
            0xAE7B,
            0xAE7C,
            0xAE7D,
            0xAE84,
            0xAE85,
            0xAE8C,
            0xAEBC,
            0xAEBD,
            0xAEBE,
            0xAEC0,
            0xAEC4,
            0xAECC,
            0xAECD,
            0xAECF,
            0xAED0,
            0xAED1,
            0xAED8,
            0xAED9,
            0xAEDC,
            0xAEE8,
            0xAEEB,
            0xAEED,
            0xAEF4,
            0xAEF8,
            0xAEFC,
            0xAF07,
            0xAF08,
            0xAF0D,
            0xAF10,
            0xAF2C,
            0xAF2D,
            0xAF30,
            0xAF32,
            0xAF34,
            0xAF3C,
            0xAF3D,
            0xAF3F,
            0xAF41,
            0xAF42,
            0xAF43,
            0xAF48,
            0xAF49,
            0xAF50,
            0xAF5C,
            0xAF5D,
            0xAF64,
            0xAF65,
            0xAF79,
            0xAF80,
            0xAF84,
            0xAF88,
            0xAF90,
            0xAF91,
            0xAF95,
            0xAF9C,
            0xAFB8,
            0xAFB9,
            0xAFBC,
            0xAFC0,
            0xAFC7,
            0xAFC8,
            0xAFC9,
            0xAFCB,
            0xAFCD,
            0xAFCE,
            0xAFD4,
            0xAFDC,
            0xAFE8,
            0xAFE9,
            0xAFF0,
            0xAFF1,
            0xAFF4,
            0xAFF8,
            0xB000,
            0xB001,
            0xB004,
            0xB00C,
            0xB010,
            0xB014,
            0xB01C,
            0xB01D,
            0xB028,
            0xB044,
            0xB045,
            0xB048,
            0xB04A,
            0xB04C,
            0xB04E,
            0xB053,
            0xB054,
            0xB055,
            0xB057,
            0xB059,
            0xB05D,
            0xB07C,
            0xB07D,
            0xB080,
            0xB084,
            0xB08C,
            0xB08D,
            0xB08F,
            0xB091,
            0xB098,
            0xB099,
            0xB09A,
            0xB09C,
            0xB09F,
            0xB0A0,
            0xB0A1,
            0xB0A2,
            0xB0A8,
            0xB0A9,
            0xB0AB,
            0xB0AC,
            0xB0AD,
            0xB0AE,
            0xB0AF,
            0xB0B1,
            0xB0B3,
            0xB0B4,
            0xB0B5,
            0xB0B8,
            0xB0BC,
            0xB0C4,
            0xB0C5,
            0xB0C7,
            0xB0C8,
            0xB0C9,
            0xB0D0,
            0xB0D1,
            0xB0D4,
            0xB0D8,
            0xB0E0,
            0xB0E5,
            0xB108,
            0xB109,
            0xB10B,
            0xB10C,
            0xB110,
            0xB112,
            0xB113,
            0xB118,
            0xB119,
            0xB11B,
            0xB11C,
            0xB11D,
            0xB123,
            0xB124,
            0xB125,
            0xB128,
            0xB12C,
            0xB134,
            0xB135,
            0xB137,
            0xB138,
            0xB139,
            0xB140,
            0xB141,
            0xB144,
            0xB148,
            0xB150,
            0xB151,
            0xB154,
            0xB155,
            0xB158,
            0xB15C,
            0xB160,
            0xB178,
            0xB179,
            0xB17C,
            0xB180,
            0xB182,
            0xB188,
            0xB189,
            0xB18B,
            0xB18D,
            0xB192,
            0xB193,
            0xB194,
            0xB198,
            0xB19C,
            0xB1A8,
            0xB1CC,
            0xB1D0,
            0xB1D4,
            0xB1DC,
            0xB1DD,
            0xB1DF,
            0xB1E8,
            0xB1E9,
            0xB1EC,
            0xB1F0,
            0xB1F9,
            0xB1FB,
            0xB1FD,
            0xB204,
            0xB205,
            0xB208,
            0xB20B,
            0xB20C,
            0xB214,
            0xB215,
            0xB217,
            0xB219,
            0xB220,
            0xB234,
            0xB23C,
            0xB258,
            0xB25C,
            0xB260,
            0xB268,
            0xB269,
            0xB274,
            0xB275,
            0xB27C,
            0xB284,
            0xB285,
            0xB289,
            0xB290,
            0xB291,
            0xB294,
            0xB298,
            0xB299,
            0xB29A,
            0xB2A0,
            0xB2A1,
            0xB2A3,
            0xB2A5,
            0xB2A6,
            0xB2AA,
            0xB2AC,
            0xB2B0,
            0xB2B4,
            0xB2C8,
            0xB2C9,
            0xB2CC,
            0xB2D0,
            0xB2D2,
            0xB2D8,
            0xB2D9,
            0xB2DB,
            0xB2DD,
            0xB2E2,
            0xB2E4,
            0xB2E5,
            0xB2E6,
            0xB2E8,
            0xB2EB,
            0xB2EC,
            0xB2ED,
            0xB2EE,
            0xB2EF,
            0xB2F3,
            0xB2F4,
            0xB2F5,
            0xB2F7,
            0xB2F8,
            0xB2F9,
            0xB2FA,
            0xB2FB,
            0xB2FF,
            0xB300,
            0xB301,
            0xB304,
            0xB308,
            0xB310,
            0xB311,
            0xB313,
            0xB314,
            0xB315,
            0xB31C,
            0xB354,
            0xB355,
            0xB356,
            0xB358,
            0xB35B,
            0xB35C,
            0xB35E,
            0xB35F,
            0xB364,
            0xB365,
            0xB367,
            0xB369,
            0xB36B,
            0xB36E,
            0xB370,
            0xB371,
            0xB374,
            0xB378,
            0xB380,
            0xB381,
            0xB383,
            0xB384,
            0xB385,
            0xB38C,
            0xB390,
            0xB394,
            0xB3A0,
            0xB3A1,
            0xB3A8,
            0xB3AC,
            0xB3C4,
            0xB3C5,
            0xB3C8,
            0xB3CB,
            0xB3CC,
            0xB3CE,
            0xB3D0,
            0xB3D4,
            0xB3D5,
            0xB3D7,
            0xB3D9,
            0xB3DB,
            0xB3DD,
            0xB3E0,
            0xB3E4,
            0xB3E8,
            0xB3FC,
            0xB410,
            0xB418,
            0xB41C,
            0xB420,
            0xB428,
            0xB429,
            0xB42B,
            0xB434,
            0xB450,
            0xB451,
            0xB454,
            0xB458,
            0xB460,
            0xB461,
            0xB463,
            0xB465,
            0xB46C,
            0xB480,
            0xB488,
            0xB49D,
            0xB4A4,
            0xB4A8,
            0xB4AC,
            0xB4B5,
            0xB4B7,
            0xB4B9,
            0xB4C0,
            0xB4C4,
            0xB4C8,
            0xB4D0,
            0xB4D5,
            0xB4DC,
            0xB4DD,
            0xB4E0,
            0xB4E3,
            0xB4E4,
            0xB4E6,
            0xB4EC,
            0xB4ED,
            0xB4EF,
            0xB4F1,
            0xB4F8,
            0xB514,
            0xB515,
            0xB518,
            0xB51B,
            0xB51C,
            0xB524,
            0xB525,
            0xB527,
            0xB528,
            0xB529,
            0xB52A,
            0xB530,
            0xB531,
            0xB534,
            0xB538,
            0xB540,
            0xB541,
            0xB543,
            0xB544,
            0xB545,
            0xB54B,
            0xB54C,
            0xB54D,
            0xB550,
            0xB554,
            0xB55C,
            0xB55D,
            0xB55F,
            0xB560,
            0xB561,
            0xB5A0,
            0xB5A1,
            0xB5A4,
            0xB5A8,
            0xB5AA,
            0xB5AB,
            0xB5B0,
            0xB5B1,
            0xB5B3,
            0xB5B4,
            0xB5B5,
            0xB5BB,
            0xB5BC,
            0xB5BD,
            0xB5C0,
            0xB5C4,
            0xB5CC,
            0xB5CD,
            0xB5CF,
            0xB5D0,
            0xB5D1,
            0xB5D8,
            0xB5EC,
            0xB610,
            0xB611,
            0xB614,
            0xB618,
            0xB625,
            0xB62C,
            0xB634,
            0xB648,
            0xB664,
            0xB668,
            0xB69C,
            0xB69D,
            0xB6A0,
            0xB6A4,
            0xB6AB,
            0xB6AC,
            0xB6B1,
            0xB6D4,
            0xB6F0,
            0xB6F4,
            0xB6F8,
            0xB700,
            0xB701,
            0xB705,
            0xB728,
            0xB729,
            0xB72C,
            0xB72F,
            0xB730,
            0xB738,
            0xB739,
            0xB73B,
            0xB744,
            0xB748,
            0xB74C,
            0xB754,
            0xB755,
            0xB760,
            0xB764,
            0xB768,
            0xB770,
            0xB771,
            0xB773,
            0xB775,
            0xB77C,
            0xB77D,
            0xB780,
            0xB784,
            0xB78C,
            0xB78D,
            0xB78F,
            0xB790,
            0xB791,
            0xB792,
            0xB796,
            0xB797,
            0xB798,
            0xB799,
            0xB79C,
            0xB7A0,
            0xB7A8,
            0xB7A9,
            0xB7AB,
            0xB7AC,
            0xB7AD,
            0xB7B4,
            0xB7B5,
            0xB7B8,
            0xB7C7,
            0xB7C9,
            0xB7EC,
            0xB7ED,
            0xB7F0,
            0xB7F4,
            0xB7FC,
            0xB7FD,
            0xB7FF,
            0xB800,
            0xB801,
            0xB807,
            0xB808,
            0xB809,
            0xB80C,
            0xB810,
            0xB818,
            0xB819,
            0xB81B,
            0xB81D,
            0xB824,
            0xB825,
            0xB828,
            0xB82C,
            0xB834,
            0xB835,
            0xB837,
            0xB838,
            0xB839,
            0xB840,
            0xB844,
            0xB851,
            0xB853,
            0xB85C,
            0xB85D,
            0xB860,
            0xB864,
            0xB86C,
            0xB86D,
            0xB86F,
            0xB871,
            0xB878,
            0xB87C,
            0xB88D,
            0xB8A8,
            0xB8B0,
            0xB8B4,
            0xB8B8,
            0xB8C0,
            0xB8C1,
            0xB8C3,
            0xB8C5,
            0xB8CC,
            0xB8D0,
            0xB8D4,
            0xB8DD,
            0xB8DF,
            0xB8E1,
            0xB8E8,
            0xB8E9,
            0xB8EC,
            0xB8F0,
            0xB8F8,
            0xB8F9,
            0xB8FB,
            0xB8FD,
            0xB904,
            0xB918,
            0xB920,
            0xB93C,
            0xB93D,
            0xB940,
            0xB944,
            0xB94C,
            0xB94F,
            0xB951,
            0xB958,
            0xB959,
            0xB95C,
            0xB960,
            0xB968,
            0xB969,
            0xB96B,
            0xB96D,
            0xB974,
            0xB975,
            0xB978,
            0xB97C,
            0xB984,
            0xB985,
            0xB987,
            0xB989,
            0xB98A,
            0xB98D,
            0xB98E,
            0xB9AC,
            0xB9AD,
            0xB9B0,
            0xB9B4,
            0xB9BC,
            0xB9BD,
            0xB9BF,
            0xB9C1,
            0xB9C8,
            0xB9C9,
            0xB9CC,
            0xB9CE,
            0xB9CF,
            0xB9D0,
            0xB9D1,
            0xB9D2,
            0xB9D8,
            0xB9D9,
            0xB9DB,
            0xB9DD,
            0xB9DE,
            0xB9E1,
            0xB9E3,
            0xB9E4,
            0xB9E5,
            0xB9E8,
            0xB9EC,
            0xB9F4,
            0xB9F5,
            0xB9F7,
            0xB9F8,
            0xB9F9,
            0xB9FA,
            0xBA00,
            0xBA01,
            0xBA08,
            0xBA15,
            0xBA38,
            0xBA39,
            0xBA3C,
            0xBA40,
            0xBA42,
            0xBA48,
            0xBA49,
            0xBA4B,
            0xBA4D,
            0xBA4E,
            0xBA53,
            0xBA54,
            0xBA55,
            0xBA58,
            0xBA5C,
            0xBA64,
            0xBA65,
            0xBA67,
            0xBA68,
            0xBA69,
            0xBA70,
            0xBA71,
            0xBA74,
            0xBA78,
            0xBA83,
            0xBA84,
            0xBA85,
            0xBA87,
            0xBA8C,
            0xBAA8,
            0xBAA9,
            0xBAAB,
            0xBAAC,
            0xBAB0,
            0xBAB2,
            0xBAB8,
            0xBAB9,
            0xBABB,
            0xBABD,
            0xBAC4,
            0xBAC8,
            0xBAD8,
            0xBAD9,
            0xBAFC,
            0xBB00,
            0xBB04,
            0xBB0D,
            0xBB0F,
            0xBB11,
            0xBB18,
            0xBB1C,
            0xBB20,
            0xBB29,
            0xBB2B,
            0xBB34,
            0xBB35,
            0xBB36,
            0xBB38,
            0xBB3B,
            0xBB3C,
            0xBB3D,
            0xBB3E,
            0xBB44,
            0xBB45,
            0xBB47,
            0xBB49,
            0xBB4D,
            0xBB4F,
            0xBB50,
            0xBB54,
            0xBB58,
            0xBB61,
            0xBB63,
            0xBB6C,
            0xBB88,
            0xBB8C,
            0xBB90,
            0xBBA4,
            0xBBA8,
            0xBBAC,
            0xBBB4,
            0xBBB7,
            0xBBC0,
            0xBBC4,
            0xBBC8,
            0xBBD0,
            0xBBD3,
            0xBBF8,
            0xBBF9,
            0xBBFC,
            0xBBFF,
            0xBC00,
            0xBC02,
            0xBC08,
            0xBC09,
            0xBC0B,
            0xBC0C,
            0xBC0D,
            0xBC0F,
            0xBC11,
            0xBC14,
            0xBC15,
            0xBC16,
            0xBC17,
            0xBC18,
            0xBC1B,
            0xBC1C,
            0xBC1D,
            0xBC1E,
            0xBC1F,
            0xBC24,
            0xBC25,
            0xBC27,
            0xBC29,
            0xBC2D,
            0xBC30,
            0xBC31,
            0xBC34,
            0xBC38,
            0xBC40,
            0xBC41,
            0xBC43,
            0xBC44,
            0xBC45,
            0xBC49,
            0xBC4C,
            0xBC4D,
            0xBC50,
            0xBC5D,
            0xBC84,
            0xBC85,
            0xBC88,
            0xBC8B,
            0xBC8C,
            0xBC8E,
            0xBC94,
            0xBC95,
            0xBC97,
            0xBC99,
            0xBC9A,
            0xBCA0,
            0xBCA1,
            0xBCA4,
            0xBCA7,
            0xBCA8,
            0xBCB0,
            0xBCB1,
            0xBCB3,
            0xBCB4,
            0xBCB5,
            0xBCBC,
            0xBCBD,
            0xBCC0,
            0xBCC4,
            0xBCCD,
            0xBCCF,
            0xBCD0,
            0xBCD1,
            0xBCD5,
            0xBCD8,
            0xBCDC,
            0xBCF4,
            0xBCF5,
            0xBCF6,
            0xBCF8,
            0xBCFC,
            0xBD04,
            0xBD05,
            0xBD07,
            0xBD09,
            0xBD10,
            0xBD14,
            0xBD24,
            0xBD2C,
            0xBD40,
            0xBD48,
            0xBD49,
            0xBD4C,
            0xBD50,
            0xBD58,
            0xBD59,
            0xBD64,
            0xBD68,
            0xBD80,
            0xBD81,
            0xBD84,
            0xBD87,
            0xBD88,
            0xBD89,
            0xBD8A,
            0xBD90,
            0xBD91,
            0xBD93,
            0xBD95,
            0xBD99,
            0xBD9A,
            0xBD9C,
            0xBDA4,
            0xBDB0,
            0xBDB8,
            0xBDD4,
            0xBDD5,
            0xBDD8,
            0xBDDC,
            0xBDE9,
            0xBDF0,
            0xBDF4,
            0xBDF8,
            0xBE00,
            0xBE03,
            0xBE05,
            0xBE0C,
            0xBE0D,
            0xBE10,
            0xBE14,
            0xBE1C,
            0xBE1D,
            0xBE1F,
            0xBE44,
            0xBE45,
            0xBE48,
            0xBE4C,
            0xBE4E,
            0xBE54,
            0xBE55,
            0xBE57,
            0xBE59,
            0xBE5A,
            0xBE5B,
            0xBE60,
            0xBE61,
            0xBE64,
            0xBE68,
            0xBE6A,
            0xBE70,
            0xBE71,
            0xBE73,
            0xBE74,
            0xBE75,
            0xBE7B,
            0xBE7C,
            0xBE7D,
            0xBE80,
            0xBE84,
            0xBE8C,
            0xBE8D,
            0xBE8F,
            0xBE90,
            0xBE91,
            0xBE98,
            0xBE99,
            0xBEA8,
            0xBED0,
            0xBED1,
            0xBED4,
            0xBED7,
            0xBED8,
            0xBEE0,
            0xBEE3,
            0xBEE4,
            0xBEE5,
            0xBEEC,
            0xBF01,
            0xBF08,
            0xBF09,
            0xBF18,
            0xBF19,
            0xBF1B,
            0xBF1C,
            0xBF1D,
            0xBF40,
            0xBF41,
            0xBF44,
            0xBF48,
            0xBF50,
            0xBF51,
            0xBF55,
            0xBF94,
            0xBFB0,
            0xBFC5,
            0xBFCC,
            0xBFCD,
            0xBFD0,
            0xBFD4,
            0xBFDC,
            0xBFDF,
            0xBFE1,
            0xC03C,
            0xC051,
            0xC058,
            0xC05C,
            0xC060,
            0xC068,
            0xC069,
            0xC090,
            0xC091,
            0xC094,
            0xC098,
            0xC0A0,
            0xC0A1,
            0xC0A3,
            0xC0A5,
            0xC0AC,
            0xC0AD,
            0xC0AF,
            0xC0B0,
            0xC0B3,
            0xC0B4,
            0xC0B5,
            0xC0B6,
            0xC0BC,
            0xC0BD,
            0xC0BF,
            0xC0C0,
            0xC0C1,
            0xC0C5,
            0xC0C8,
            0xC0C9,
            0xC0CC,
            0xC0D0,
            0xC0D8,
            0xC0D9,
            0xC0DB,
            0xC0DC,
            0xC0DD,
            0xC0E4,
            0xC0E5,
            0xC0E8,
            0xC0EC,
            0xC0F4,
            0xC0F5,
            0xC0F7,
            0xC0F9,
            0xC100,
            0xC104,
            0xC108,
            0xC110,
            0xC115,
            0xC11C,
            0xC11D,
            0xC11E,
            0xC11F,
            0xC120,
            0xC123,
            0xC124,
            0xC126,
            0xC127,
            0xC12C,
            0xC12D,
            0xC12F,
            0xC130,
            0xC131,
            0xC136,
            0xC138,
            0xC139,
            0xC13C,
            0xC140,
            0xC148,
            0xC149,
            0xC14B,
            0xC14C,
            0xC14D,
            0xC154,
            0xC155,
            0xC158,
            0xC15C,
            0xC164,
            0xC165,
            0xC167,
            0xC168,
            0xC169,
            0xC170,
            0xC174,
            0xC178,
            0xC185,
            0xC18C,
            0xC18D,
            0xC18E,
            0xC190,
            0xC194,
            0xC196,
            0xC19C,
            0xC19D,
            0xC19F,
            0xC1A1,
            0xC1A5,
            0xC1A8,
            0xC1A9,
            0xC1AC,
            0xC1B0,
            0xC1BD,
            0xC1C4,
            0xC1C8,
            0xC1CC,
            0xC1D4,
            0xC1D7,
            0xC1D8,
            0xC1E0,
            0xC1E4,
            0xC1E8,
            0xC1F0,
            0xC1F1,
            0xC1F3,
            0xC1FC,
            0xC1FD,
            0xC200,
            0xC204,
            0xC20C,
            0xC20D,
            0xC20F,
            0xC211,
            0xC218,
            0xC219,
            0xC21C,
            0xC21F,
            0xC220,
            0xC228,
            0xC229,
            0xC22B,
            0xC22D,
            0xC22F,
            0xC231,
            0xC232,
            0xC234,
            0xC248,
            0xC250,
            0xC251,
            0xC254,
            0xC258,
            0xC260,
            0xC265,
            0xC26C,
            0xC26D,
            0xC270,
            0xC274,
            0xC27C,
            0xC27D,
            0xC27F,
            0xC281,
            0xC288,
            0xC289,
            0xC290,
            0xC298,
            0xC29B,
            0xC29D,
            0xC2A4,
            0xC2A5,
            0xC2A8,
            0xC2AC,
            0xC2AD,
            0xC2B4,
            0xC2B5,
            0xC2B7,
            0xC2B9,
            0xC2DC,
            0xC2DD,
            0xC2E0,
            0xC2E3,
            0xC2E4,
            0xC2EB,
            0xC2EC,
            0xC2ED,
            0xC2EF,
            0xC2F1,
            0xC2F6,
            0xC2F8,
            0xC2F9,
            0xC2FB,
            0xC2FC,
            0xC300,
            0xC308,
            0xC309,
            0xC30C,
            0xC30D,
            0xC313,
            0xC314,
            0xC315,
            0xC318,
            0xC31C,
            0xC324,
            0xC325,
            0xC328,
            0xC329,
            0xC345,
            0xC368,
            0xC369,
            0xC36C,
            0xC370,
            0xC372,
            0xC378,
            0xC379,
            0xC37C,
            0xC37D,
            0xC384,
            0xC388,
            0xC38C,
            0xC3C0,
            0xC3D8,
            0xC3D9,
            0xC3DC,
            0xC3DF,
            0xC3E0,
            0xC3E2,
            0xC3E8,
            0xC3E9,
            0xC3ED,
            0xC3F4,
            0xC3F5,
            0xC3F8,
            0xC408,
            0xC410,
            0xC424,
            0xC42C,
            0xC430,
            0xC434,
            0xC43C,
            0xC43D,
            0xC448,
            0xC464,
            0xC465,
            0xC468,
            0xC46C,
            0xC474,
            0xC475,
            0xC479,
            0xC480,
            0xC494,
            0xC49C,
            0xC4B8,
            0xC4BC,
            0xC4E9,
            0xC4F0,
            0xC4F1,
            0xC4F4,
            0xC4F8,
            0xC4FA,
            0xC4FF,
            0xC500,
            0xC501,
            0xC50C,
            0xC510,
            0xC514,
            0xC51C,
            0xC528,
            0xC529,
            0xC52C,
            0xC530,
            0xC538,
            0xC539,
            0xC53B,
            0xC53D,
            0xC544,
            0xC545,
            0xC548,
            0xC549,
            0xC54A,
            0xC54C,
            0xC54D,
            0xC54E,
            0xC553,
            0xC554,
            0xC555,
            0xC557,
            0xC558,
            0xC559,
            0xC55D,
            0xC55E,
            0xC560,
            0xC561,
            0xC564,
            0xC568,
            0xC570,
            0xC571,
            0xC573,
            0xC574,
            0xC575,
            0xC57C,
            0xC57D,
            0xC580,
            0xC584,
            0xC587,
            0xC58C,
            0xC58D,
            0xC58F,
            0xC591,
            0xC595,
            0xC597,
            0xC598,
            0xC59C,
            0xC5A0,
            0xC5A9,
            0xC5B4,
            0xC5B5,
            0xC5B8,
            0xC5B9,
            0xC5BB,
            0xC5BC,
            0xC5BD,
            0xC5BE,
            0xC5C4,
            0xC5C5,
            0xC5C6,
            0xC5C7,
            0xC5C8,
            0xC5C9,
            0xC5CA,
            0xC5CC,
            0xC5CE,
            0xC5D0,
            0xC5D1,
            0xC5D4,
            0xC5D8,
            0xC5E0,
            0xC5E1,
            0xC5E3,
            0xC5E5,
            0xC5EC,
            0xC5ED,
            0xC5EE,
            0xC5F0,
            0xC5F4,
            0xC5F6,
            0xC5F7,
            0xC5FC,
            0xC5FD,
            0xC5FE,
            0xC5FF,
            0xC600,
            0xC601,
            0xC605,
            0xC606,
            0xC607,
            0xC608,
            0xC60C,
            0xC610,
            0xC618,
            0xC619,
            0xC61B,
            0xC61C,
            0xC624,
            0xC625,
            0xC628,
            0xC62C,
            0xC62D,
            0xC62E,
            0xC630,
            0xC633,
            0xC634,
            0xC635,
            0xC637,
            0xC639,
            0xC63B,
            0xC640,
            0xC641,
            0xC644,
            0xC648,
            0xC650,
            0xC651,
            0xC653,
            0xC654,
            0xC655,
            0xC65C,
            0xC65D,
            0xC660,
            0xC66C,
            0xC66F,
            0xC671,
            0xC678,
            0xC679,
            0xC67C,
            0xC680,
            0xC688,
            0xC689,
            0xC68B,
            0xC68D,
            0xC694,
            0xC695,
            0xC698,
            0xC69C,
            0xC6A4,
            0xC6A5,
            0xC6A7,
            0xC6A9,
            0xC6B0,
            0xC6B1,
            0xC6B4,
            0xC6B8,
            0xC6B9,
            0xC6BA,
            0xC6C0,
            0xC6C1,
            0xC6C3,
            0xC6C5,
            0xC6CC,
            0xC6CD,
            0xC6D0,
            0xC6D4,
            0xC6DC,
            0xC6DD,
            0xC6E0,
            0xC6E1,
            0xC6E8,
            0xC6E9,
            0xC6EC,
            0xC6F0,
            0xC6F8,
            0xC6F9,
            0xC6FD,
            0xC704,
            0xC705,
            0xC708,
            0xC70C,
            0xC714,
            0xC715,
            0xC717,
            0xC719,
            0xC720,
            0xC721,
            0xC724,
            0xC728,
            0xC730,
            0xC731,
            0xC733,
            0xC735,
            0xC737,
            0xC73C,
            0xC73D,
            0xC740,
            0xC744,
            0xC74A,
            0xC74C,
            0xC74D,
            0xC74F,
            0xC751,
            0xC752,
            0xC753,
            0xC754,
            0xC755,
            0xC756,
            0xC757,
            0xC758,
            0xC75C,
            0xC760,
            0xC768,
            0xC76B,
            0xC774,
            0xC775,
            0xC778,
            0xC77C,
            0xC77D,
            0xC77E,
            0xC783,
            0xC784,
            0xC785,
            0xC787,
            0xC788,
            0xC789,
            0xC78A,
            0xC78E,
            0xC790,
            0xC791,
            0xC794,
            0xC796,
            0xC797,
            0xC798,
            0xC79A,
            0xC7A0,
            0xC7A1,
            0xC7A3,
            0xC7A4,
            0xC7A5,
            0xC7A6,
            0xC7AC,
            0xC7AD,
            0xC7B0,
            0xC7B4,
            0xC7BC,
            0xC7BD,
            0xC7BF,
            0xC7C0,
            0xC7C1,
            0xC7C8,
            0xC7C9,
            0xC7CC,
            0xC7CE,
            0xC7D0,
            0xC7D8,
            0xC7DD,
            0xC7E4,
            0xC7E8,
            0xC7EC,
            0xC800,
            0xC801,
            0xC804,
            0xC808,
            0xC80A,
            0xC810,
            0xC811,
            0xC813,
            0xC815,
            0xC816,
            0xC81C,
            0xC81D,
            0xC820,
            0xC824,
            0xC82C,
            0xC82D,
            0xC82F,
            0xC831,
            0xC838,
            0xC83C,
            0xC840,
            0xC848,
            0xC849,
            0xC84C,
            0xC84D,
            0xC854,
            0xC870,
            0xC871,
            0xC874,
            0xC878,
            0xC87A,
            0xC880,
            0xC881,
            0xC883,
            0xC885,
            0xC886,
            0xC887,
            0xC88B,
            0xC88C,
            0xC88D,
            0xC894,
            0xC89D,
            0xC89F,
            0xC8A1,
            0xC8A8,
            0xC8BC,
            0xC8BD,
            0xC8C4,
            0xC8C8,
            0xC8CC,
            0xC8D4,
            0xC8D5,
            0xC8D7,
            0xC8D9,
            0xC8E0,
            0xC8E1,
            0xC8E4,
            0xC8F5,
            0xC8FC,
            0xC8FD,
            0xC900,
            0xC904,
            0xC905,
            0xC906,
            0xC90C,
            0xC90D,
            0xC90F,
            0xC911,
            0xC918,
            0xC92C,
            0xC934,
            0xC950,
            0xC951,
            0xC954,
            0xC958,
            0xC960,
            0xC961,
            0xC963,
            0xC96C,
            0xC970,
            0xC974,
            0xC97C,
            0xC988,
            0xC989,
            0xC98C,
            0xC990,
            0xC998,
            0xC999,
            0xC99B,
            0xC99D,
            0xC9C0,
            0xC9C1,
            0xC9C4,
            0xC9C7,
            0xC9C8,
            0xC9CA,
            0xC9D0,
            0xC9D1,
            0xC9D3,
            0xC9D5,
            0xC9D6,
            0xC9D9,
            0xC9DA,
            0xC9DC,
            0xC9DD,
            0xC9E0,
            0xC9E2,
            0xC9E4,
            0xC9E7,
            0xC9EC,
            0xC9ED,
            0xC9EF,
            0xC9F0,
            0xC9F1,
            0xC9F8,
            0xC9F9,
            0xC9FC,
            0xCA00,
            0xCA08,
            0xCA09,
            0xCA0B,
            0xCA0C,
            0xCA0D,
            0xCA14,
            0xCA18,
            0xCA29,
            0xCA4C,
            0xCA4D,
            0xCA50,
            0xCA54,
            0xCA5C,
            0xCA5D,
            0xCA5F,
            0xCA60,
            0xCA61,
            0xCA68,
            0xCA7D,
            0xCA84,
            0xCA98,
            0xCABC,
            0xCABD,
            0xCAC0,
            0xCAC4,
            0xCACC,
            0xCACD,
            0xCACF,
            0xCAD1,
            0xCAD3,
            0xCAD8,
            0xCAD9,
            0xCAE0,
            0xCAEC,
            0xCAF4,
            0xCB08,
            0xCB10,
            0xCB14,
            0xCB18,
            0xCB20,
            0xCB21,
            0xCB41,
            0xCB48,
            0xCB49,
            0xCB4C,
            0xCB50,
            0xCB58,
            0xCB59,
            0xCB5D,
            0xCB64,
            0xCB78,
            0xCB79,
            0xCB9C,
            0xCBB8,
            0xCBD4,
            0xCBE4,
            0xCBE7,
            0xCBE9,
            0xCC0C,
            0xCC0D,
            0xCC10,
            0xCC14,
            0xCC1C,
            0xCC1D,
            0xCC21,
            0xCC22,
            0xCC27,
            0xCC28,
            0xCC29,
            0xCC2C,
            0xCC2E,
            0xCC30,
            0xCC38,
            0xCC39,
            0xCC3B,
            0xCC3C,
            0xCC3D,
            0xCC3E,
            0xCC44,
            0xCC45,
            0xCC48,
            0xCC4C,
            0xCC54,
            0xCC55,
            0xCC57,
            0xCC58,
            0xCC59,
            0xCC60,
            0xCC64,
            0xCC66,
            0xCC68,
            0xCC70,
            0xCC75,
            0xCC98,
            0xCC99,
            0xCC9C,
            0xCCA0,
            0xCCA8,
            0xCCA9,
            0xCCAB,
            0xCCAC,
            0xCCAD,
            0xCCB4,
            0xCCB5,
            0xCCB8,
            0xCCBC,
            0xCCC4,
            0xCCC5,
            0xCCC7,
            0xCCC9,
            0xCCD0,
            0xCCD4,
            0xCCE4,
            0xCCEC,
            0xCCF0,
            0xCD01,
            0xCD08,
            0xCD09,
            0xCD0C,
            0xCD10,
            0xCD18,
            0xCD19,
            0xCD1B,
            0xCD1D,
            0xCD24,
            0xCD28,
            0xCD2C,
            0xCD39,
            0xCD5C,
            0xCD60,
            0xCD64,
            0xCD6C,
            0xCD6D,
            0xCD6F,
            0xCD71,
            0xCD78,
            0xCD88,
            0xCD94,
            0xCD95,
            0xCD98,
            0xCD9C,
            0xCDA4,
            0xCDA5,
            0xCDA7,
            0xCDA9,
            0xCDB0,
            0xCDC4,
            0xCDCC,
            0xCDD0,
            0xCDE8,
            0xCDEC,
            0xCDF0,
            0xCDF8,
            0xCDF9,
            0xCDFB,
            0xCDFD,
            0xCE04,
            0xCE08,
            0xCE0C,
            0xCE14,
            0xCE19,
            0xCE20,
            0xCE21,
            0xCE24,
            0xCE28,
            0xCE30,
            0xCE31,
            0xCE33,
            0xCE35,
            0xCE58,
            0xCE59,
            0xCE5C,
            0xCE5F,
            0xCE60,
            0xCE61,
            0xCE68,
            0xCE69,
            0xCE6B,
            0xCE6D,
            0xCE74,
            0xCE75,
            0xCE78,
            0xCE7C,
            0xCE84,
            0xCE85,
            0xCE87,
            0xCE89,
            0xCE90,
            0xCE91,
            0xCE94,
            0xCE98,
            0xCEA0,
            0xCEA1,
            0xCEA3,
            0xCEA4,
            0xCEA5,
            0xCEAC,
            0xCEAD,
            0xCEC1,
            0xCEE4,
            0xCEE5,
            0xCEE8,
            0xCEEB,
            0xCEEC,
            0xCEF4,
            0xCEF5,
            0xCEF7,
            0xCEF8,
            0xCEF9,
            0xCF00,
            0xCF01,
            0xCF04,
            0xCF08,
            0xCF10,
            0xCF11,
            0xCF13,
            0xCF15,
            0xCF1C,
            0xCF20,
            0xCF24,
            0xCF2C,
            0xCF2D,
            0xCF2F,
            0xCF30,
            0xCF31,
            0xCF38,
            0xCF54,
            0xCF55,
            0xCF58,
            0xCF5C,
            0xCF64,
            0xCF65,
            0xCF67,
            0xCF69,
            0xCF70,
            0xCF71,
            0xCF74,
            0xCF78,
            0xCF80,
            0xCF85,
            0xCF8C,
            0xCFA1,
            0xCFA8,
            0xCFB0,
            0xCFC4,
            0xCFE0,
            0xCFE1,
            0xCFE4,
            0xCFE8,
            0xCFF0,
            0xCFF1,
            0xCFF3,
            0xCFF5,
            0xCFFC,
            0xD000,
            0xD004,
            0xD011,
            0xD018,
            0xD02D,
            0xD034,
            0xD035,
            0xD038,
            0xD03C,
            0xD044,
            0xD045,
            0xD047,
            0xD049,
            0xD050,
            0xD054,
            0xD058,
            0xD060,
            0xD06C,
            0xD06D,
            0xD070,
            0xD074,
            0xD07C,
            0xD07D,
            0xD081,
            0xD0A4,
            0xD0A5,
            0xD0A8,
            0xD0AC,
            0xD0B4,
            0xD0B5,
            0xD0B7,
            0xD0B9,
            0xD0C0,
            0xD0C1,
            0xD0C4,
            0xD0C8,
            0xD0C9,
            0xD0D0,
            0xD0D1,
            0xD0D3,
            0xD0D4,
            0xD0D5,
            0xD0DC,
            0xD0DD,
            0xD0E0,
            0xD0E4,
            0xD0EC,
            0xD0ED,
            0xD0EF,
            0xD0F0,
            0xD0F1,
            0xD0F8,
            0xD10D,
            0xD130,
            0xD131,
            0xD134,
            0xD138,
            0xD13A,
            0xD140,
            0xD141,
            0xD143,
            0xD144,
            0xD145,
            0xD14C,
            0xD14D,
            0xD150,
            0xD154,
            0xD15C,
            0xD15D,
            0xD15F,
            0xD161,
            0xD168,
            0xD16C,
            0xD17C,
            0xD184,
            0xD188,
            0xD1A0,
            0xD1A1,
            0xD1A4,
            0xD1A8,
            0xD1B0,
            0xD1B1,
            0xD1B3,
            0xD1B5,
            0xD1BA,
            0xD1BC,
            0xD1C0,
            0xD1D8,
            0xD1F4,
            0xD1F8,
            0xD207,
            0xD209,
            0xD210,
            0xD22C,
            0xD22D,
            0xD230,
            0xD234,
            0xD23C,
            0xD23D,
            0xD23F,
            0xD241,
            0xD248,
            0xD25C,
            0xD264,
            0xD280,
            0xD281,
            0xD284,
            0xD288,
            0xD290,
            0xD291,
            0xD295,
            0xD29C,
            0xD2A0,
            0xD2A4,
            0xD2AC,
            0xD2B1,
            0xD2B8,
            0xD2B9,
            0xD2BC,
            0xD2BF,
            0xD2C0,
            0xD2C2,
            0xD2C8,
            0xD2C9,
            0xD2CB,
            0xD2D4,
            0xD2D8,
            0xD2DC,
            0xD2E4,
            0xD2E5,
            0xD2F0,
            0xD2F1,
            0xD2F4,
            0xD2F8,
            0xD300,
            0xD301,
            0xD303,
            0xD305,
            0xD30C,
            0xD30D,
            0xD30E,
            0xD310,
            0xD314,
            0xD316,
            0xD31C,
            0xD31D,
            0xD31F,
            0xD320,
            0xD321,
            0xD325,
            0xD328,
            0xD329,
            0xD32C,
            0xD330,
            0xD338,
            0xD339,
            0xD33B,
            0xD33C,
            0xD33D,
            0xD344,
            0xD345,
            0xD37C,
            0xD37D,
            0xD380,
            0xD384,
            0xD38C,
            0xD38D,
            0xD38F,
            0xD390,
            0xD391,
            0xD398,
            0xD399,
            0xD39C,
            0xD3A0,
            0xD3A8,
            0xD3A9,
            0xD3AB,
            0xD3AD,
            0xD3B4,
            0xD3B8,
            0xD3BC,
            0xD3C4,
            0xD3C5,
            0xD3C8,
            0xD3C9,
            0xD3D0,
            0xD3D8,
            0xD3E1,
            0xD3E3,
            0xD3EC,
            0xD3ED,
            0xD3F0,
            0xD3F4,
            0xD3FC,
            0xD3FD,
            0xD3FF,
            0xD401,
            0xD408,
            0xD41D,
            0xD440,
            0xD444,
            0xD45C,
            0xD460,
            0xD464,
            0xD46D,
            0xD46F,
            0xD478,
            0xD479,
            0xD47C,
            0xD47F,
            0xD480,
            0xD482,
            0xD488,
            0xD489,
            0xD48B,
            0xD48D,
            0xD494,
            0xD4A9,
            0xD4CC,
            0xD4D0,
            0xD4D4,
            0xD4DC,
            0xD4DF,
            0xD4E8,
            0xD4EC,
            0xD4F0,
            0xD4F8,
            0xD4FB,
            0xD4FD,
            0xD504,
            0xD508,
            0xD50C,
            0xD514,
            0xD515,
            0xD517,
            0xD53C,
            0xD53D,
            0xD540,
            0xD544,
            0xD54C,
            0xD54D,
            0xD54F,
            0xD551,
            0xD558,
            0xD559,
            0xD55C,
            0xD560,
            0xD565,
            0xD568,
            0xD569,
            0xD56B,
            0xD56D,
            0xD574,
            0xD575,
            0xD578,
            0xD57C,
            0xD584,
            0xD585,
            0xD587,
            0xD588,
            0xD589,
            0xD590,
            0xD5A5,
            0xD5C8,
            0xD5C9,
            0xD5CC,
            0xD5D0,
            0xD5D2,
            0xD5D8,
            0xD5D9,
            0xD5DB,
            0xD5DD,
            0xD5E4,
            0xD5E5,
            0xD5E8,
            0xD5EC,
            0xD5F4,
            0xD5F5,
            0xD5F7,
            0xD5F9,
            0xD600,
            0xD601,
            0xD604,
            0xD608,
            0xD610,
            0xD611,
            0xD613,
            0xD614,
            0xD615,
            0xD61C,
            0xD620,
            0xD624,
            0xD62D,
            0xD638,
            0xD639,
            0xD63C,
            0xD640,
            0xD645,
            0xD648,
            0xD649,
            0xD64B,
            0xD64D,
            0xD651,
            0xD654,
            0xD655,
            0xD658,
            0xD65C,
            0xD667,
            0xD669,
            0xD670,
            0xD671,
            0xD674,
            0xD683,
            0xD685,
            0xD68C,
            0xD68D,
            0xD690,
            0xD694,
            0xD69D,
            0xD69F,
            0xD6A1,
            0xD6A8,
            0xD6AC,
            0xD6B0,
            0xD6B9,
            0xD6BB,
            0xD6C4,
            0xD6C5,
            0xD6C8,
            0xD6CC,
            0xD6D1,
            0xD6D4,
            0xD6D7,
            0xD6D9,
            0xD6E0,
            0xD6E4,
            0xD6E8,
            0xD6F0,
            0xD6F5,
            0xD6FC,
            0xD6FD,
            0xD700,
            0xD704,
            0xD711,
            0xD718,
            0xD719,
            0xD71C,
            0xD720,
            0xD728,
            0xD729,
            0xD72B,
            0xD72D,
            0xD734,
            0xD735,
            0xD738,
            0xD73C,
            0xD744,
            0xD747,
            0xD749,
            0xD750,
            0xD751,
            0xD754,
            0xD756,
            0xD757,
            0xD758,
            0xD759,
            0xD760,
            0xD761,
            0xD763,
            0xD765,
            0xD769,
            0xD76C,
            0xD770,
            0xD774,
            0xD77C,
            0xD77D,
            0xD781,
            0xD788,
            0xD789,
            0xD78C,
            0xD790,
            0xD798,
            0xD799,
            0xD79B,
            0xD79D
    };
    private static final char[] INITIAL_SOUND = {                                                                                        // 19
            12593,                                                                                                                                                                                        // ㄱ
            12594,                                                                                                                                                                                        // ㄲ
            12596,                                                                                                                                                                                        // ㄴ
            12599,                                                                                                                                                                                        // ㄷ
            12600,                                                                                                                                                                                        // ㄸ
            12601,                                                                                                                                                                                        // ㄹ
            12609,                                                                                                                                                                                        // ㅁ
            12610,                                                                                                                                                                                        // ㅂ
            12611,                                                                                                                                                                                        // ㅃ
            12613,                                                                                                                                                                                        // ㅅ
            12614,                                                                                                                                                                                        // ㅆ
            12615,                                                                                                                                                                                        // ㅇ
            12616,                                                                                                                                                                                        // ㅈ
            12617,                                                                                                                                                                                        // ㅉ
            12618,                                                                                                                                                                                        // ㅊ
            12619,                                                                                                                                                                                        // ㅋ
            12620,                                                                                                                                                                                        // ㅌ
            12621,                                                                                                                                                                                        // ㅍ
            12622
            // ㅎ
    };
    private static final char[] MIDDLE_SOUND = {                                                                                        // 21
            12623,                                                                                                                                                                                        // ㅏ
            12624,                                                                                                                                                                                        // ㅐ
            12625,                                                                                                                                                                                        // ㅑ
            12626,                                                                                                                                                                                        // ㅒ
            12627,                                                                                                                                                                                        // ㅓ
            12628,                                                                                                                                                                                        // ㅔ
            12629,                                                                                                                                                                                        // ㅕ
            12630,                                                                                                                                                                                        // ㅖ
            12631,                                                                                                                                                                                        // ㅗ
            12632,                                                                                                                                                                                        // ㅘ
            12633,                                                                                                                                                                                        // ㅙ
            12634,                                                                                                                                                                                        // ㅚ
            12635,                                                                                                                                                                                        // ㅛ
            12636,                                                                                                                                                                                        // ㅜ
            12637,                                                                                                                                                                                        // ㅝ
            12638,                                                                                                                                                                                        // ㅞ
            12639,                                                                                                                                                                                        // ㅟ
            12640,                                                                                                                                                                                        // ㅠ
            12641,                                                                                                                                                                                        // ㅡ
            12642,                                                                                                                                                                                        // ㅢ
            12643
            // ㅣ
    };
    private static final char[] FINAL_SOUND = {                                                                                        // 28
            32,                                                                                                                                                                                                // space
            12593,                                                                                                                                                                                        // ㄱ
            12594,                                                                                                                                                                                        // ㄲ
            12595,                                                                                                                                                                                        // ㄳ
            12596,                                                                                                                                                                                        // ㄴ
            12597,                                                                                                                                                                                        // ㄵ
            12598,                                                                                                                                                                                        // ㄶ
            12599,                                                                                                                                                                                        // ㄷ
            12601,                                                                                                                                                                                        // ㄹ
            12602,                                                                                                                                                                                        // ㄺ
            12603,                                                                                                                                                                                        // ㄻ
            12604,                                                                                                                                                                                        // ㄼ
            12605,                                                                                                                                                                                        // ㄽ
            12606,                                                                                                                                                                                        // ㄾ
            12607,                                                                                                                                                                                        // ㄿ
            12608,                                                                                                                                                                                        // ㅀ
            12609,                                                                                                                                                                                        // ㅁ
            12610,                                                                                                                                                                                        // ㅂ
            12612,                                                                                                                                                                                        // ㅄ
            12613,                                                                                                                                                                                        // ㅅ
            12614,                                                                                                                                                                                        // ㅆ
            12615,                                                                                                                                                                                        // ㅇ
            12616,                                                                                                                                                                                        // ㅈ
            12618,                                                                                                                                                                                        // ㅊ
            12619,                                                                                                                                                                                        // ㅋ
            12620,                                                                                                                                                                                        // ㅌ
            12621,                                                                                                                                                                                        // ㅍ
            12622
            // ㅎ
    };
    private static final char[] CHANGE_INITIAL_SOUND = {                                                                                        // 19
            12593,                                                                                                                                                                                        // ㄱ
            12593,                                                                                                                                                                                        // ㄲ->ㄱ
            12596,                                                                                                                                                                                        // ㄴ
            12599,                                                                                                                                                                                        // ㄷ
            12599,                                                                                                                                                                                        // ㄸ->ㄷ
            12601,                                                                                                                                                                                        // ㄹ
            12609,                                                                                                                                                                                        // ㅁ
            12610,                                                                                                                                                                                        // ㅂ
            12610,                                                                                                                                                                                        // ㅃ->ㅂ
            12613,                                                                                                                                                                                        // ㅅ
            12613,                                                                                                                                                                                        // ㅆ->ㅅ
            12615,                                                                                                                                                                                        // ㅇ
            12616,                                                                                                                                                                                        // ㅈ
            12616,                                                                                                                                                                                        // ㅉ->ㅈ
            12618,                                                                                                                                                                                        // ㅊ
            12619,                                                                                                                                                                                        // ㅋ
            12620,                                                                                                                                                                                        // ㅌ
            12621,                                                                                                                                                                                        // ㅍ
            12622
            // ㅎ
    };
    private static final char[] CHANGE_MIDDLE_SOUND = {                                                                                        // 21
            12623,                                                                                                                                                                                        // ㅏ
            12624,                                                                                                                                                                                        // ㅐ
            12625,                                                                                                                                                                                        // ㅑ
            12624,                                                                                                                                                                                        // ㅒ->ㅐ
            12627,                                                                                                                                                                                        // ㅓ
            12628,                                                                                                                                                                                        // ㅔ
            12629,                                                                                                                                                                                        // ㅕ
            12628,                                                                                                                                                                                        // ㅖ->ㅔ
            12631,                                                                                                                                                                                        // ㅗ
            12623,                                                                                                                                                                                        // ㅘ->ㅏ
            12624,                                                                                                                                                                                        // ㅙ->ㅐ
            12624,                                                                                                                                                                                        // ㅚ->ㅐ
            12635,                                                                                                                                                                                        // ㅛ
            12636,                                                                                                                                                                                        // ㅜ
            12627,                                                                                                                                                                                        // ㅝ->ㅓ
            12628,                                                                                                                                                                                        // ㅞ->ㅔ
            12643,                                                                                                                                                                                        // ㅟ->ㅣ
            12640,                                                                                                                                                                                        // ㅠ
            12641,                                                                                                                                                                                        // ㅡ
            12641,                                                                                                                                                                                        // ㅢ->ㅡ
            12643
            // ㅣ
    };
    private static final char[] CHANGE_FINAL_SOUND = {                                                                                        // 28
            32,                                                                                                                                                                                                // space
            12593,                                                                                                                                                                                        // ㄱ
            12593,                                                                                                                                                                                        // ㄲ->ㄱ
            12593,                                                                                                                                                                                        // ㄳ->ㄱ
            12596,                                                                                                                                                                                        // ㄴ
            12596,                                                                                                                                                                                        // ㄵ->ㄴ
            12596,                                                                                                                                                                                        // ㄶ->
            12599,                                                                                                                                                                                        // ㄷ
            12601,                                                                                                                                                                                        // ㄹ
            12593,                                                                                                                                                                                        // ㄺ->ㄱ
            12609,                                                                                                                                                                                        // ㄻ->ㅁ
            12610,                                                                                                                                                                                        // ㄼ->ㅂ
            12613,                                                                                                                                                                                        // ㄽ->ㅅ
            12620,                                                                                                                                                                                        // ㄾ->ㅌ
            12621,                                                                                                                                                                                        // ㄿ->ㅍ
            12622,                                                                                                                                                                                        // ㅀ->ㅎ
            12609,                                                                                                                                                                                        // ㅁ
            12610,                                                                                                                                                                                        // ㅂ
            12610,                                                                                                                                                                                        // ㅄ->ㅂ
            12613,                                                                                                                                                                                        // ㅅ
            12613,                                                                                                                                                                                        // ㅆ->ㅅ
            12615,                                                                                                                                                                                        // ㅇ
            12616,                                                                                                                                                                                        // ㅈ
            12616,                                                                                                                                                                                        // ㅊ->ㅈ
            12593,                                                                                                                                                                                        // ㅋ->ㄱ
            12599,                                                                                                                                                                                        // ㅌ->ㄷ
            12610,                                                                                                                                                                                        // ㅍ->ㅂ
            12622
            // ㅎ
    };

    /**
     * EUC-KR 에서 지원되는 한글인지 확인
     *
     * @param c char
     * @return boolean true면 EUC-KR 에서 지원되는 한글
     */
    public static boolean isH2350(char c) {
        if (HANGUL_2350_STRINGBUFFER.length() == 0)
            HANGUL_2350_STRINGBUFFER.append(HANGUL_2350);
        return HANGUL_2350_STRINGBUFFER.indexOf(String.valueOf(c)) > -1;
    }

    /**
     * 초성, 중성, 종성 캐릭터를 모아서 한글 한글자를 만든다.
     *
     * @param initialSoundChar char 초성
     * @param middleSoundChar  char 중성
     * @param finalSoundChar   char 종성
     * @return String 한글 한글자
     */
    public static String combineChar(char initialSoundChar, char middleSoundChar, char finalSoundChar) {
        StringBuilder a = new StringBuilder();
        StringBuilder b = new StringBuilder();
        StringBuilder c = new StringBuilder();

        a.append(INITIAL_SOUND);
        b.append(MIDDLE_SOUND);
        c.append(FINAL_SOUND);

        int initialSoundPos = a.indexOf(String.valueOf(initialSoundChar));
        int middleSoundPos = b.indexOf(String.valueOf(middleSoundChar));
        int finalSoundPos = c.indexOf(String.valueOf(finalSoundChar));

        return String.valueOf((char) (44032 + (initialSoundPos * (MIDDLE_SOUND.length * FINAL_SOUND.length)) + (middleSoundPos * FINAL_SOUND.length) + (finalSoundPos)));
    }

    /**
     * 유니코드 완성형 한글(11172자) -> EUC-KR 완성형 한글(2350자)로 치환
     *
     * @param str 원본 문자열
     * @return String 치환된 문자열
     */
    public static String convertH2350(String str) {
        StringBuilder source = new StringBuilder(str);

        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);

            // 한글이 아니면 통과
            if (c < 44032 || c > 44032 + 11172)
                continue;

            // 유니코드면
            if (!isH2350(c)) {
                int x = (int) source.charAt(i) - 44032;

                int initialPos = x / (MIDDLE_SOUND.length * FINAL_SOUND.length);
                int middlePos = x % (MIDDLE_SOUND.length * FINAL_SOUND.length) / FINAL_SOUND.length;
                int finalPos = x % (MIDDLE_SOUND.length * FINAL_SOUND.length) % FINAL_SOUND.length;

                logger.info("치환할 문자 : 초성[{}], 중성[{}], 종성[{}]", INITIAL_SOUND[initialPos], MIDDLE_SOUND[middlePos], FINAL_SOUND[finalPos]);

                // 초성변환
                String tmp = combineChar(CHANGE_INITIAL_SOUND[initialPos], MIDDLE_SOUND[middlePos], FINAL_SOUND[finalPos]);

                // 유니코드면
                if (!isH2350(tmp.charAt(0))) {
                    // 종성변환
                    tmp = combineChar(CHANGE_INITIAL_SOUND[initialPos], MIDDLE_SOUND[middlePos], CHANGE_FINAL_SOUND[finalPos]);

                    // 유니코드면
                    if (!isH2350(tmp.charAt(0))) {
                        // 중성 변환
                        tmp = combineChar(CHANGE_INITIAL_SOUND[initialPos], CHANGE_MIDDLE_SOUND[middlePos], CHANGE_FINAL_SOUND[finalPos]);

                        // 유니코드면
                        if (!isH2350(tmp.charAt(0)))
                            tmp = " ";
                    }
                }

                logger.info("치환된 문자 : {}", tmp);
                source.setCharAt(i, tmp.charAt(0));
            }
        }
        return source.toString();
    }

    /**
     * 캐릭터 크기
     *
     * @param str str
     * @return int
     */
    public static int getCharacterLength(String str) {
        int len = str.length();
        int cnt = 0;

        for (int i = 0; i < len; i++) {
            if (str.charAt(i) < 256)
                cnt++;
            else
                cnt += 2;
        }

        return cnt;
    }

    /**
     * 문자 byte 크기
     *
     * @param str str
     * @return int
     */
    public static int getStringLength(String str) {
        int strLen = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < 0xac00 || 0xd7a3 < c) {
                strLen++;
            } else {
                strLen += 2;
            }
        }

        return strLen;
    }

    /**
     * 문자 바이트 크기
     *
     * @param str str
     * @return int
     */
    public static int getByteLength(String str) {
        byte[] strByte = str.getBytes();
        int len = 0;
        for (int i = 0; i < strByte.length; i++)
            len++;

        return len;
    }

    /**
     * 한글 한글자 바이트 수
     *
     * @return int
     */
    public static int getKoreanByteLength() {
        return KOREAN_BYTE_LENGTH;
    }

    /**
     * 한글 문자를 검사
     *
     * @param ch char
     * @return boolean
     */
    public static boolean isKoreanCharacter(char ch) {
        String block = Character.UnicodeBlock.of(ch).toString();

        return block.equals("HANGUL_JAMO") || block.equals("HANGUL_SYLLABLES") || block.equals("HANGUL_COMPATIBILITY_JAMO");
    }

    /**
     * 입력 문자열에 한 글이 존재하는지 검사
     *
     * @param source source
     * @return boolean
     */
    public static boolean isKoreanCharacterContained(String source) {
        if (isBlank(source))
            return false;

        for (char ch : source.toCharArray()) {
            if (isKoreanCharacter(ch))
                return true;
        }

        return false;
    }

    public static String[] stringTokenizer(String str, String delim) {
        if (str == null) {
            return null;
        } else {
            StringTokenizer st = new StringTokenizer(str, delim);

            ArrayList<String> resultList = new ArrayList<>();

            while (st.hasMoreTokens()) {
                resultList.add(st.nextToken().trim());
            }

            String[] result = new String[resultList.size()];
            resultList.toArray(result);

            return result;
        }
    }

    /**
     * HTML 에서 표현될 특수 문자(&amp;, \n, &nbsp;, &lt;, &gt;, &quot;) 처리
     *
     * @param source source
     * @return String
     */
    public static String toHtml(String source) {
        if (source == null) {
            return null;
        } else {
            source = replace(source, "&", "&amp;");
            source = replace(source, "<", "&lt;");
            source = replace(source, ">", "&gt;");
            source = replace(source, "\n", "<br>");
            source = replace(source, "\r", "<br>");
            source = replace(source, "\r\n", "<br>");
            source = replace(source, "\"", "&quot;");
            return source;
        }
    }

    /**
     * HTML 에서 표현될 특수 문자(&amp;, \n, &nbsp;, &lt;, &gt;, &quot;) 처리
     *
     * @param source source
     * @return String
     */
    public static String toHtml2(String source) {
        if (source == null) {
            return null;
        } else {
            source = replace(source, "&", "&amp;");
            source = replace(source, "<", "&lt;");
            source = replace(source, ">", "&gt;");
            source = replace(source, "\"", "&quot;");
            return source;
        }
    }

    public static String stringReplace(String str) {
        /*
         * int str_length = str.length(); String strlistchar = "";
         */
        String str_imsi;

        String[] filter_word = {
                "",
                "\\.",
                "\\?",
                "\\/",
                "\\~",
                "\\!",
                "\\@",
                "\\#",
                "\\$",
                "\\%",
                "\\^",
                "\\&",
                "\\*",
                "\\(",
                "\\)",
                "\\_",
                "\\+",
                "\\=",
                "\\|",
                "\\\\",
                "\\}",
                "\\]",
                "\\{",
                "\\[",
                "\\\"",
                "\\'",
                "\\:",
                "\\;",
                "\\<",
                "\\,",
                "\\>",
                "\\.",
                "\\?",
                "\\/"
        };

        int i = 0;
        while (i < filter_word.length) {
            String aFilter_word = filter_word[i];
            str_imsi = str.replaceAll(aFilter_word, "");
            str = str_imsi;
            i++;
        }

        return str;
    }

    /**
     * HTML 에서 text box 에 들어갈 특수 문자(&quot;) 처리
     *
     * @param source source
     * @return String
     */
    public static String toText(String source) {
        if (source == null) {
            return null;
        } else {
            source = replace(source, "\"", "&quot;");
            return source;
        }
    }

    public static String ltrim(String source, String trimStr) {
        if (source != null && source.startsWith(trimStr)) {
            return source.substring(trimStr.length());
        }
        return source;
    }

    public static String rtrim(String source, String trimStr) {
        if (source != null && source.endsWith(trimStr)) {
            return source.substring(0, source.length() - trimStr.length());
        }
        return source;
    }

    public static String decryptString(String s) {
        if (s == null || s.equals(""))
            return "";

        char ac[] = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            ac[i] = s.charAt(i);
        }

        char ac1[] = new char[s.length()];
        for (int j = 0; j < s.length(); j++) {
            ac1[j] = (char) ((ac[j] + s.length()) - (j + 1) - 32);
        }

        return new String(ac1);
    }

    public static String encryptString(String s) {
        if (s == null || s.equals(""))
            return "";

        char ac[] = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            ac[i] = s.charAt(i);
        }

        char ac1[] = new char[s.length()];
        for (int j = 0; j < s.length(); j++) {
            ac1[j] = (char) ((ac[j] - s.length()) + (j + 1) + 32);
        }

        return new String(ac1);
    }

    public static String pwdDecode(String s) {
        if (s == null || s.equals(""))
            return "";

        char ac[] = new char[s.length()];
        String as[] = {
                "aF",
                "bC",
                "c9",
                "dN",
                "eU",
                "fo",
                "gv",
                "hw",
                "iD",
                "jE",
                "kO",
                "lc",
                "mS",
                "nj",
                "oV",
                "pn",
                "qQ",
                "rZ",
                "sa",
                "td",
                "uY",
                "ve",
                "wq",
                "x4",
                "yP",
                "zi",
                "AG",
                "BK",
                "C8",
                "DB",
                "Eg",
                "F7",
                "GI",
                "H5",
                "Ip",
                "Js",
                "Kt",
                "LW",
                "MX",
                "N6",
                "OR",
                "Pk",
                "QT",
                "R2",
                "Sy",
                "Tl",
                "Um",
                "Vx",
                "WA",
                "X1",
                "Yr",
                "ZM",
                "0f",
                "1J",
                "2h",
                "3z",
                "40",
                "5u",
                "63",
                "7L",
                "8H",
                "9b",
                null
        };
        for (int i = 0; i < s.length(); i++) {
            int j;
            for (j = 0; as[j] != null; j++) {
                if (s.charAt(i) == as[j].charAt(1)) {
                    ac[i] = as[j].charAt(0);
                    break;
                }
            }

            if (as[j] == null)
                ac[i] = ' ';
        }

        return new String(ac);
    }

    public static String pwdEncode(String s) {
        char ac[] = new char[s.length()];

        String as[] = {
                "aF",
                "bC",
                "c9",
                "dN",
                "eU",
                "fo",
                "gv",
                "hw",
                "iD",
                "jE",
                "kO",
                "lc",
                "mS",
                "nj",
                "oV",
                "pn",
                "qQ",
                "rZ",
                "sa",
                "td",
                "uY",
                "ve",
                "wq",
                "x4",
                "yP",
                "zi",
                "AG",
                "BK",
                "C8",
                "DB",
                "Eg",
                "F7",
                "GI",
                "H5",
                "Ip",
                "Js",
                "Kt",
                "LW",
                "MX",
                "N6",
                "OR",
                "Pk",
                "QT",
                "R2",
                "Sy",
                "Tl",
                "Um",
                "Vx",
                "WA",
                "X1",
                "Yr",
                "ZM",
                "0f",
                "1J",
                "2h",
                "3z",
                "40",
                "5u",
                "63",
                "7L",
                "8H",
                "9b",
                null
        };
        for (int i = 0; i < s.length(); i++) {
            int j;
            for (j = 0; as[j] != null; j++) {
                if (s.charAt(i) == as[j].charAt(0)) {
                    ac[i] = as[j].charAt(1);
                    break;
                }
            }

            if (as[j] == null)
                ac[i] = ' ';
        }

        return new String(ac);
    }

    /**
     * ISO8859_1로 인코딩된 문자열을 EUC-KR로 변환한다.
     *
     * @param str8859 8859_1로 encoding된 String
     * @return EUC-KR로 변환된 String
     */
    public static String toKor(String str8859) {

        if (str8859 == null)
            return null;

        String str5601 = null;

        try {
            str5601 = new String(str8859.getBytes("8859_1"), "euc-kr");
        } catch (UnsupportedEncodingException e) {
            str5601 = str8859;
        }

        return str5601;
    }

    /**
     * EUC-KR로 인코딩된 문자열을 ISO8859_1로 변환한다.
     *
     * @param str5601 EUC-KR로 encoding된 String
     * @return ISO8859_1로 변환된 String
     */
    public static String toEng(String str5601) {

        if (str5601 == null)
            return null;

        String str8859 = null;

        try {
            str8859 = new String(str5601.getBytes("euc-kr"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            str8859 = str5601;
        }

        return str8859;
    }

    /**
     * <code>Reader</code> 로 부터 String 을 얻는다.
     *
     * @param is is
     * @return String
     * @throws IOException IOException
     */
    public static String getStringFromReader(Reader is) throws IOException {
        if (is == null)
            return null;

        StringWriter sw = new StringWriter();
        char[] buffer = new char[1024];
        int length;

        try {
            while ((length = is.read(buffer)) != -1) {
                sw.write(buffer, 0, length);
            }
            is.close();
        } finally {
            is.close();
        }

        return sw.toString();
    }

    /**
     * 필터리스트를 가지고 필터링처리
     *
     * @param source  필터링할 문자열
     * @param filters 필터리스트
     * @param delim   필터구분자
     * @param toWord  변경할 문자열
     * @return String 필터링된 문자열
     */
    public static String filterString(String source, String filters, String delim, String toWord) {

        String[] filterList = stringTokenizer(filters, delim);

        if (source == null) {
            return null;
        } else {
            for (int i = 0; i < filterList.length; i++) {
                source = replace(source, filterList[i], toWord);
            }

            return source;
        }
    }

    /**
     * 문자열중사이즈 를 체크하여 ...을 붙여준다. Case p=0 Contents Write Case p=1 Reply >
     *
     * @param str     처리할 문자열
     * @param size    자를 문자열수
     * @param backstr 뒤에 붙을 문자
     * @return String 처리된 문자열
     */
    public static String toTruncate(String str, int size, String backstr) {

        String ret;

        if (str != null && str.length() > size) {
            ret = str.substring(0, size) + backstr;
        } else {
            ret = str;
        }
        return ret;
    }

    /**
     * HTML 문서에서 태그를 제거하고 텍스트만 추출한다.
     *
     * @param html HTML 스트링
     * @return String
     */
    public static String removeHtmlTag(String html) {
        if (html == null || html.trim().equals(""))
            return "";

        final int NORMAL_STATE = 0;
        final int TAG_STATE = 1;
        final int START_TAG_STATE = 2;
        final int END_TAG_STATE = 3;
        final int SINGLE_QUOT_STATE = 4;
        final int DOUBLE_QUOT_STATE = 5;
        int state = NORMAL_STATE;
        int oldState = NORMAL_STATE;
        char[] chars = html.toCharArray();
        StringBuffer sb = new StringBuffer();
        char a;
        for (int i = 0; i < chars.length; i++) {
            a = chars[i];
            switch (state) {
                case NORMAL_STATE:
                    if (a == '<')
                        state = TAG_STATE;
                    else
                        sb.append(a);
                    break;
                case TAG_STATE:
                    if (a == '>')
                        state = NORMAL_STATE;
                    else if (a == '\"') {
                        oldState = state;
                        state = DOUBLE_QUOT_STATE;
                    } else if (a == '\'') {
                        oldState = state;
                        state = SINGLE_QUOT_STATE;
                    } else if (a == '/')
                        state = END_TAG_STATE;
                    else if (a != ' ' && a != '\t' && a != '\n' && a != '\r' && a != '\f')
                        state = START_TAG_STATE;
                    break;
                case START_TAG_STATE:
                case END_TAG_STATE:
                    if (a == '>')
                        state = NORMAL_STATE;
                    else if (a == '\"') {
                        oldState = state;
                        state = DOUBLE_QUOT_STATE;
                    } else if (a == '\'') {
                        oldState = state;
                        state = SINGLE_QUOT_STATE;
                    } else if (a == '\"')
                        state = DOUBLE_QUOT_STATE;
                    else if (a == '\'')
                        state = SINGLE_QUOT_STATE;
                    break;
                case DOUBLE_QUOT_STATE:
                    if (a == '\"')
                        state = oldState;
                    break;
                case SINGLE_QUOT_STATE:
                    if (a == '\'')
                        state = oldState;
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 날짜스트링을 포맷팅
     *
     * @param source source
     * @return String (ex. 2008.05.07)
     */
    public static String toFrmDate(String source) {
        if (source == null) {
            return "";
        } else if (source.length() == 8 || source.length() == 14) {
            source = source.substring(0, 4) + "." + source.substring(4, 6) + "." + source.substring(6, 8);
            return source;
        } else {
            return source;
        }
    }

    /**
     * 날짜스트링을 포맷팅
     *
     * @param source source
     * @param deli   deli
     * @return String (ex. 2008.05.07)
     */
    public static String toFrmDate(String source, String deli) {
        if (source == null) {
            return "";
        } else if (source.length() == 8 || source.length() == 12 || source.length() == 14) {
            source = source.substring(0, 4) + deli + source.substring(4, 6) + deli + source.substring(6, 8);
            return source;
        } else {
            return source;
        }
    }

    /**
     * 날짜스트링을 포맷팅
     *
     * @param source source
     * @return String (ex. 08/05/07 15:20)
     */
    public static String toLongFrmDate(String source) {
        if (source == null) {
            return "";
        } else if (source.length() == 12) {
            source = source.substring(2, 4) + "/" + source.substring(4, 6) + "/" + source.substring(6, 8) + " " + source.substring(8, 10) + ":" + source.substring(10, 12);
            return source;
        } else if (source.length() == 14) {
            source = source.substring(2, 4) + "/"
                    + source.substring(4, 6)
                    + "/"
                    + source.substring(6, 8)
                    + " "
                    + source.substring(8, 10)
                    + ":"
                    + source.substring(10, 12)
                    + ":"
                    + source.substring(12, 14);
            return source;
        } else {
            return source;
        }
    }

    /**
     * 금액스트링을 포맷팅
     *
     * @param source source
     * @return String (ex. 1,000,000)
     */
    public static String toFrmCost(String source) {
        if (source == null) {
            return "";
        } else {
            int length = source.length();
            int co = 3;
            String temp = "";
            while (length > 0) {
                length = length - co;
                if (length < 0) {
                    co = length + co;
                    length = 0;
                }
                temp = "," + source.substring(length, co + length) + temp;
            }
            return temp.replaceFirst(",", "");
        }
    }

    public static String nvl(String source, String target) {
        if (isEmpty(source))
            return target;
        else
            return source;
    }

    public static String NVL(String source, String target) {
        return nvl(source, target);
    }

    public static String isNull(String source, String target) {
        return nvl(source, target);
    }

    public static String ifNull(String source, String target) {
        return nvl(source, target);
    }

    /**
     * 문자열이 null or 문자열의 길이가 0 인면 TRUE, source.trim()
     *
     * @param source
     * @return 문자열이 null or 문자열의 길이가 0 이면 false
     */
    public static boolean isNotBlank(String source) {
        if (source == null || source.trim().length() <= 0)
            return false;
        else
            return true;
    }

    /**
     * 문자열의 길이가 0 인지 판단, StringUtil.isEmpty("   ") = false
     *
     * @param source
     * @return 문자열의 길이가 0 이면 true
     */
    public static boolean isEmpty(String source) {
        if (source == null || source.length() <= 0)
            return true;
        else
            return false;
    }

    /**
     * 문자열이 null or 문자열의 길이가 0 인면 TRUE
     *
     * @param source
     * @return 문자열이 null or 문자열의 길이가 0 이면 false
     */
    public static boolean isNotEmpty(String source) {
        if (source == null || source.length() <= 0)
            return false;
        else
            return true;
    }

    /**
     * 문자열이 null 이면 NullPointerException 발생 null이 아니면, 입력 파라미터를 return
     *
     * @param source
     * @return
     * @throws NullPointerException
     */
    public static String isNullPointer(String source) throws NullPointerException {
        if (source == null)
            throw new NullPointerException();

        return source;
    }

    /**
     * 파마리터로 받은 source의 앞 뒤에, CDATA 태그를 입힘
     *
     * @param source
     * @return
     */
    public static String addCDATATag(String source) {
        StringBuffer str = new StringBuffer();
        return str.append(StringUtil.FROM_CDATA).append(source).append(StringUtil.TO_CDATA).toString();
    }

    /**
     * 개행 문자(화면에 출력시 다음 행으로 줄 변경)를 제거
     *
     * @param source
     * @return
     */
    public static String eliminateLF(String source) {
        return source.replaceAll("\n", "");
    }

    /**
     * 캐리지 리턴(행의 처음으로 커서를 이동)을 제거
     *
     * @param source
     * @return
     */
    public static String eliminateCR(String source) {
        return source.replaceAll("\r", "");
    }

    public static String eliminateCRLF(String source) {
        // return eliminateCarriageReturn(eliminateLineFeed(source));
        return source.replaceAll("\n", "").replaceAll("\r", "");
    }

    /**
     * 한글,숫자,영문을 제외한 문자 필터
     *
     * @param source
     * @return String
     */
    public static String filterSpecialWord(String source) {
        source = source.replaceAll("[^가-힣a-zA-Z0-9]", "");
        return source;
    }

    /**
     * 한글 글자수 카운트
     *
     * @param source
     * @return int
     */
    public static int getHangulCount(String source) {
        Pattern p = Pattern.compile("[가-힣]");
        Matcher m = p.matcher(source);
        int count = 0;
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * <pre>
     * 한자가 있는 문자열을 받아서 한자를 없앰 &lt;br&gt;
     * 캐릭터 단위로 유니코드 비교 한글 : 0xAC00 &tilde; 0xD7A3&lt;br&gt;
     * 한자 4E00 &tilde; 9FFF , F900 &tilde; FAFF
     * 한자 앞뒤에 있는 괄호도 삭제
     * 1. 한글 범위에 없을경우&lt;br&gt;
     * 2. 한자 바로 앞 뒤에 괄호가 있는 경우 괄호도 삭제&lt;br&gt;
     * *주의* 괄호안에 한자이외의 공백 등 다른문자는 그대로 출력됨 &lt;br&gt;
     * 즉 (安寧) 이경우만 괄호와 한자 정상 삭제&lt;br&gt;
     * &#064;param str 한자가 있는 문자열
     * </pre>
     */
    public static String deleteHanja(String str) {
        /** 시작 시간 */
        // long startTime = System.currentTimeMillis();
        StringBuffer stb = new StringBuffer();
        char[] charArray = str.toCharArray();
        int temp = 0;
        // int index = str.length();

        for (int i = 0; i < charArray.length; i++) {
            temp = (int) charArray[i];
            if (charArray[i] == '(') {
                temp = (int) charArray[i + 1];
                /** 괄호 다음에 한자가 나올경우 괄호 삭제 * 한글,아스키 문자 이외의 모든것 */
                // if(temp>128 && (temp < '\uAC00' || temp > '\uD7A3'))
                /** 한자만 검색 */
                if ((temp >= '\u4E00' && temp <= '\u9FFF') || (temp >= '\uF900' && temp <= '\uFAFF')) {
                    continue;
                }
            }
            /**
             * 현재 문자가 한글이 아닐 경우 괄호 다음에 한자가 나올경우 괄호 삭제 한글,아스키 문자 이외의 모든것
             */
            // else if(temp>128 && (temp < '\uAC00' || temp > '\uD7A3'))
            /** 한자만 검색 */
            else if ((temp >= '\u4E00' && temp <= '\u9FFF') || (temp >= '\uF900' && temp <= '\uFAFF')) {
                /** 다음 문자가 ')' 일 경우 건너뜀 */
                if (i < charArray.length - 1) {
                    if (charArray[i + 1] == ')') {
                        i++;
                    }
                }
                continue;
            }
            /** 문자를 스트링 버퍼에 추가 */
            stb.append(charArray[i]);
        }
        // long endTime = System.currentTimeMillis();
        return stb.toString();
    }

    /**
     * 정규식을 이용해 문자열를 검색한다.
     *
     * @param source 원본 문자열
     * @param regex  정규식
     * @return ArrayList<String>
     */
    public static List<String> findStrByRegx(String source, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        List<String> result = new ArrayList<String>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    /**
     * 정규식을 이용해 문자열를 검색한다.
     *
     * @param source 원본 문자열
     * @param regex  정규식
     * @return ArrayList<String>
     */
    public static List<String> findStrByRegx(String source, String regex, int index) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        List<String> result = new ArrayList<String>();
        while (matcher.find()) {
            result.add(matcher.group(index));
        }
        return result;
    }

    /**
     * 정규식을 이용해 문자열를 치환한다.
     *
     * @param source      원본 문자열
     * @param regex       정규식
     * @param replacement 치환문자
     * @return ArrayList<String>
     */
    public static List<String> replaceStrByRegx(String source, String regex, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        List<String> result = new ArrayList<String>();
        while (matcher.find()) {
            result.add(matcher.group().replaceAll(regex, replacement));
        }
        return result;
    }

    public static String replaceOneStrByRegx(String source, String regex, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        String result = null;
        if (matcher.find()) {
            result = matcher.group().replaceAll(regex, replacement);
        }
        return result;
    }

    /**
     * 바이트 단위로 글자를 자른다. 한글처리 포함.
     *
     * @param source 원본 문자열
     * @param size   자르려는 바이트 크기
     * @return 문자열
     */
    public static String byteSubstr(String source, int size) {
        byte[] tmp = source.getBytes();
        StringBuffer result = new StringBuffer();
        // 자르려는 크기가 문자열크기보다 크면 자르려는 크기를 문자열 크기로 조정한다
        if (tmp.length < size)
            size = tmp.length;

        for (int i = 0; i < size; i++) {
            // 한글이라면
            if (tmp[i] < 0) {
                // 자르려는 곳에 한글이 걸치면 그 한글은 제외 시킨다.
                if (i + (KOREAN_BYTE_LENGTH) > size)
                    break;

                // 한글의 한글자 바이트 수대로 덧 붙이고
                result.append(new String(tmp, i, KOREAN_BYTE_LENGTH));
                // 한글의 한글자 바이트 수만큼 건너뛴다
                i += (KOREAN_BYTE_LENGTH - 1);
            } else {
                result.append(new String(tmp, i, 1));
            }
        }
        return result.toString();
    }

    /**
     * StringUtil.padding(3, 'e') = "eee"
     *
     * @param repeat
     * @param padChar
     * @return
     * @throws IndexOutOfBoundsException
     */
    public static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        final char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }

    /**
     * 문자열 자르기 문자열을 5자리 문자열 자리수 만큼 자른다. 모자라면 0으로 채우고 초과되면 5자까지 보내줌
     */
    public static String cutString(String str, int size, String pad) {
        if (str == null) {
            return null;
        }
        int cutter = str.length() - size;
        if (cutter < 0) {
            return rightPad(str, 5, pad);
        } else if (cutter == 0) {
            return str;
        } else {
            return str.substring(0, 5);
        }
    }

    /**
     * 문자열 오른쪽에 space(' ')을 덧 붙임 StringUtil.rightPad("", 3) = "   " StringUtil.rightPad("bat", 1) = "bat"
     * StringUtil.rightPad("bat", 3) = "bat" StringUtil.rightPad("bat", 5) = "bat  "
     *
     * @param str  str
     * @param size size
     * @return
     */
    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }

    public static String rightPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return rightPad(str, size, String.valueOf(padChar));
        }
        return str.concat(padding(pads, padChar));
    }

    public static String rightPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return rightPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return str.concat(padStr);
        } else if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return str.concat(new String(padding));
        }
    }

    /**
     * 문자열 왼쪽에 space(' ')을 덧 붙임 StringUtil.leftPad("", 3) = "   " StringUtil.leftPad("bat", 1) = "bat"
     * StringUtil.leftPad("bat", 3) = "bat" StringUtil.leftPad("bat", 5) = "  bat"
     *
     * @param str
     * @param size
     * @return
     */
    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return padding(pads, padChar).concat(str);
    }

    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }

    public static double mult(double x, double privateKey, double modulus) {
        double y = 1;
        while (privateKey > 0) {
            while ((privateKey / 2) == (int) (privateKey / 2)) {
                x = (x * x) % modulus;
                privateKey = privateKey / 2;
            }
            y = (x * y) % modulus;
            privateKey = privateKey - 1;
        }
        ;
        return y;
    }

    /**
     * 들어온 Url 중 특정 파라미터의 값을 삭제 처리 한다.
     *
     * @param urlString
     * @param removeParameter
     * @return
     */
    public static String removeUrlParameter(String urlString, String removeParameter) {

        String retUrlString = "";
        String thePattern1 = "\\?" + removeParameter + "=\\w*\\&";
        String thePattern2 = "\\&" + removeParameter + "=\\w*";
        String replacementString = "";

        Pattern pattern = Pattern.compile(thePattern1);
        Matcher match = pattern.matcher(urlString);
        if (match.find()) {
            replacementString = "?";
            retUrlString = match.replaceAll(replacementString);
        } else {
            pattern = Pattern.compile(thePattern2);
            match = pattern.matcher(urlString);

            if (match.find()) {
                replacementString = "";
                retUrlString = match.replaceAll(replacementString);
            } else {
                retUrlString = urlString;
            }
        }

        return retUrlString;
    }

    /**
     * str 문자열에서, sub 문자열의 개수를 찾아서 리턴
     *
     * @param str
     * @param sub
     * @return
     */
    public static int countMatches(String str, String sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    /**
     * str 문자열에서, sub 문자열의 위치를 maxCnt 개수 만큼 찾아서 마지막 maxCnt에 해당되는 문자열의 마지막 문자 index를 리턴 만약, 문자열에서 sub 문자열의 개수가 maxCnt 만큼
     * 되지 않으면 -1을 리턴함
     *
     * @param str
     * @param sub
     * @param maxCnt
     * @return
     */
    public static int countMachesIndex(String str, String sub, int maxCnt) {
        if (isEmpty(str) || isEmpty(sub))
            return 0;

        int index = -1, cnt = 0;
        int length = str.length();

        while (index < length && cnt++ < maxCnt) {
            index = str.indexOf(sub, index);
            if (index < 0) // 미발견
                break;
            else
                index += 1;
        }

        return index;
    }

    /**
     * @param str
     * @param sub
     * @param page
     * @return
     */
    public static String contentPaging(String str, String sub, int page) {
        return contentPaging(str, sub, 10, page);
    }

    public static String contentPaging(String str, String sub, int cnt, int page) {
        if (isEmpty(str) || isEmpty(sub) || page <= 0)
            return str;
        if (cnt <= 0)
            cnt = 10;

        int maxCnt = cnt * page;
        int endIndex = countMachesIndex(str, sub, maxCnt);
        int startIndex = 0;

        if (endIndex < 0)
            endIndex = str.length();
        else if (endIndex < str.length())
            endIndex = endIndex + (sub.length() - 1);

        if (page == 0)
            startIndex = 0;
        else {
            startIndex = countMachesIndex(str, sub, cnt * (page - 1));
            if (startIndex < 0)
                startIndex = 0;
            else if (startIndex > 0)
                startIndex += (sub.length() - 1);
        }

        return str.substring(startIndex, endIndex);
    }

    public static int length(String source) {
        if (isBlank(source))
            return 0;
        else
            return source.length();
    }

    /**
     * 입력 문자열이 숫자로만 이루어져있는지 검사
     *
     * @param source
     * @return
     */
    public static boolean isNumber(String source) {
        boolean result = true;
        if (isBlank(source)) {
            result = false;
        } else {
            for (char c : source.toCharArray()) {
                if (c < 48 || c > 59) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 특정문자로 둘러쌓인 알맹이 문자
     *
     * @param str      작업대상 문자열
     * @param startTag 제거될 시작문자열
     * @param endTag   제거될 끝문자열
     * @param index    몇번제 시작문자열 부터 작업할 것인지 Index (1부터 시작)
     * @return 시작문자열, 끝문자열이 제거되고 남은 문자열
     */
    public static String getLapOutText(String str, String startTag, String endTag, int index) {
        String l_String = str;
        int l_posStartTag = 0;
        int l_lookUpPoint = 0;
        int l_findCnt = 0;
        // ---------------------------------------------------------------------
        // 시작 Tag 위치 알아 내기
        // ---------------------------------------------------------------------
        do {
            l_posStartTag = l_String.indexOf(startTag, l_lookUpPoint); // -- 시작
            // Tag의
            // 위치 알아
            // 내기
            l_findCnt++; // -- 찾은 수 증가
            l_lookUpPoint = l_posStartTag + 1; // -- 다음 검색 위치조정
        }
        while (l_findCnt < index && l_posStartTag >= 0); // -- 찾은 수가 찾을 번째 수와
        // 같을때 까지 Loop

        if (l_posStartTag < 0)
            return "";
        // ---------------------------------------------------------------------
        // 종료 Tag의 위치 알아 내기
        // ---------------------------------------------------------------------
        int l_posEndTag = l_String.indexOf(endTag, l_posStartTag);

        if (l_posEndTag < 0)
            return "";

        // ---------------------------------------------------------------------
        // Column Name 가져오기
        // ---------------------------------------------------------------------
        l_String = l_String.substring(l_posStartTag + startTag.length(), l_posEndTag).trim();

        return l_String;
    }

    /**
     * 특정문자로 둘러쌓인 알맹이 문자 치환하기
     *
     * @param str      작업대상 문자열
     * @param startTag 시작문자열
     * @param endTag   끝문자열
     * @param rStr     치환 문자열
     * @return
     */
    public static String replaceLapOutText(String str, String startTag, String endTag, String rStr) {
        String l_String = str;
        int l_posStartTag = 0;
        int l_lookUpPoint = 0;
        int l_findCnt = 0;
        int index = 1;
        // ---------------------------------------------------------------------
        // 시작 Tag 위치 알아 내기
        // ---------------------------------------------------------------------
        do {
            l_posStartTag = l_String.indexOf(startTag, l_lookUpPoint); // -- 시작
            // Tag의
            // 위치 알아
            // 내기
            l_findCnt++; // -- 찾은 수 증가
            l_lookUpPoint = l_posStartTag + 1; // -- 다음 검색 위치조정
        }
        while (l_findCnt < index && l_posStartTag >= 0); // -- 찾은 수가 찾을 번째 수와
        // 같을때 까지 Loop

        if (l_posStartTag < 0)
            return "";
        // ---------------------------------------------------------------------
        // 종료 Tag의 위치 알아 내기
        // ---------------------------------------------------------------------
        int l_posEndTag = l_String.indexOf(endTag, l_posStartTag);

        if (l_posEndTag < 0)
            return "";

        // ---------------------------------------------------------------------
        // Column Name 가져오기
        // ---------------------------------------------------------------------
        l_String = l_String.substring(0, l_posStartTag + startTag.length()) + rStr + l_String.substring(l_posEndTag, l_String.length());
        return l_String;
    }

    /**
     * html파일을 읽어들여서 String 문자열로 변환한다.
     *
     * @param str 변환할 문자열
     */

    @SuppressWarnings("resource")
    public static String readFileToString(String str) {
        try {
            StringBuffer strbuf = new StringBuffer();
            File file = new File(str);
            BufferedReader fr = new BufferedReader(new FileReader(str));

            if (file.exists()) {
                String tmp = null;
                while ((tmp = fr.readLine()) != null)
                    strbuf.append(tmp).append("\n");
            } else {
                return null;
            }

            // 바뀐 스트링을 리턴
            return strbuf.toString();

        } catch (FileNotFoundException e) {
            logger.error("readFileToString 파일을 찾을 수 없습니다. - {}", str);
            return null;
        } catch (IOException e) {
            logger.error("readFileToString IOException - {}", str);
            return "";
        }
    }

    public static void stringToWriteFile(String path, String content) {
        try {
            FileWriter out = new FileWriter(path, false);
            out.write(content);
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            logger.error("stringToWriteFile 파일을 찾을 수 없습니다. - {}", path);
        } catch (IOException e) {
            logger.error("stringToWriteFile IOException - {}", path);
        }
    }

    /**
     * 파일 내의 특정 구역 안의 내용을 치환
     *
     * @param path      파일 절대경로
     * @param changeStr 바뀔내용
     * @param startTag  특정구역의 시작 문구
     * @param endTag    특정구역의 끝 문구
     */
    public static void replaceStrAreaInFile(String path, String changeStr, String startTag, String endTag) {
        try {
            String content = readFileToString(path);
            String content2 = replaceLapOutText(content, startTag, endTag, changeStr);
            stringToWriteFile(path + "." + DateUtil.dateToString(new Date(), "yyyyMMdd"), content);// 백업파일.
            stringToWriteFile(path, content2);

        } catch (Exception e) {
            logger.error("파일 내의 특정 구역 안의 내용을 치환에 실패하였습니다. - {}", e.getMessage());
        }
    }

    /**
     * String 배열 중에서 null인 경우를 제외하고 반환(String 배열로 반환)
     *
     * @param strs
     * @return
     */
    public static String[] getNotNullStringArray(String[] strs) {
        int i = 0;
        for (String s : strs) {
            if (StringUtils.hasLength(s)) {
                i++;
            }
        }

        String[] returnStrs = new String[i];
        i = 0;
        for (String s : strs) {
            if (StringUtils.hasLength(s)) {
                returnStrs[i] = s;
                i++;
            }
        }
        return returnStrs;
    }

    /**
     * @param str
     * @return
     */
    public static boolean isAlpha(String str) {

        if (str == null) {
            return false;
        }

        int size = str.length();

        if (size == 0)
            return false;

        for (int i = 0; i < size; i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param str
     * @return
     */
    public static boolean isAlphaNumeric(String str) {

        if (str == null) {
            return false;
        }

        int size = str.length();

        if (size == 0)
            return false;

        for (int i = 0; i < size; i++) {
            if (!Character.isLetterOrDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param integer
     * @return
     */
    public static String integer2string(int integer) {
        return ("" + integer);
    }

    /**
     * @param longdata
     * @return
     */
    public static String long2string(long longdata) {
        return String.valueOf(longdata);
    }

    public static String float2string(float floatdata) {
        return String.valueOf(floatdata);
    }

    public static String double2string(double doubledata) {
        return String.valueOf(doubledata);
    }

    /**
     * @param source
     * @param target
     * @return
     */
    public static int search(String source, String target) {
        int result = 0;
        String strCheck = new String(source);
        for (int i = 0; i < source.length(); ) {
            int loc = strCheck.indexOf(target);
            if (loc == -1) {
                break;
            } else {
                result++;
                i = loc + target.length();
                strCheck = strCheck.substring(i);
            }
        }
        return result;
    }

    /**
     * @param str
     * @return
     */
    public static String ltrim(String str) {

        int index = 0;

        while (' ' == str.charAt(index++))
            ;

        if (index > 0)
            str = str.substring(index - 1);

        return str;
    }

    public static String rtrim(String str) {

        int index = str.length();

        while (' ' == str.charAt(--index))
            ;

        if (index < str.length())
            str = str.substring(0, index + 1);

        return str;
    }

    public static String concat(String str1, String str2) {

        StringBuffer sb = new StringBuffer(str1);
        sb.append(str2);

        return sb.toString();
    }

    public static String alignLeft(String str, int length) {
        return alignLeft(str, length, false);
    }

    /**
     * @param str
     * @param length
     * @param isEllipsis
     * @return
     */
    public static String alignLeft(String str, int length, boolean isEllipsis) {

        if (str.length() <= length) {

            StringBuffer temp = new StringBuffer(str);
            for (int i = 0; i < (length - str.length()); i++) {
                temp.append(WHITE_SPACE);
            }
            return temp.toString();
        } else {
            if (isEllipsis) {

                StringBuffer temp = new StringBuffer(length);
                temp.append(str.substring(0, length - 3));
                temp.append("...");

                return temp.toString();
            } else {
                return str.substring(0, length);
            }
        }
    }

    public static String alignRight(String str, int length) {

        return alignRight(str, length, false);
    }

    public static String alignRight(String str, int length, boolean isEllipsis) {

        if (str.length() <= length) {

            StringBuffer temp = new StringBuffer(length);
            for (int i = 0; i < (length - str.length()); i++) {
                temp.append(WHITE_SPACE);
            }
            temp.append(str);
            return temp.toString();
        } else {
            if (isEllipsis) {

                StringBuffer temp = new StringBuffer(length);
                temp.append(str.substring(0, length - 3));
                temp.append("...");
                return temp.toString();
            } else {
                return str.substring(0, length);
            }
        }
    }

    public static String alignCenter(String str, int length) {
        return alignCenter(str, length, false);
    }

    public static String alignCenter(String str, int length, boolean isEllipsis) {
        if (str.length() <= length) {

            StringBuffer temp = new StringBuffer(length);
            int leftMargin = (int) (length - str.length()) / 2;

            int rightMargin;
            if ((leftMargin * 2) == (length - str.length())) {
                rightMargin = leftMargin;
            } else {
                rightMargin = leftMargin + 1;
            }

            for (int i = 0; i < leftMargin; i++) {
                temp.append(WHITE_SPACE);
            }

            temp.append(str);

            for (int i = 0; i < rightMargin; i++) {
                temp.append(WHITE_SPACE);
            }

            return temp.toString();
        } else {
            if (isEllipsis) {

                StringBuffer temp = new StringBuffer(length);
                temp.append(str.substring(0, length - 3));
                temp.append("...");
                return temp.toString();
            } else {
                return str.substring(0, length);
            }
        }

    }

    /*
     * StringUtil in Anyframe
     */

    /**
     * Encode a string using algorithm specified in web.xml and return the resulting encrypted password. If exception,
     * the plain credentials string is returned
     *
     * @param password  Password or other credentials to use in authenticating this username
     * @param algorithm Algorithm used to do the digest
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;

        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            logger.error("Exception: " + e);
            return password;
        }
        md.reset();

        // call the update method one or more times
        // (useful when you don't know the size of your data, eg.
        // stream)
        md.update(unencodedPassword);

        // now calculate the hash
        byte[] encodedPassword = md.digest();

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < encodedPassword.length; i++) {
            if (((int) encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString((int) encodedPassword[i] & 0xff, 16));
        }

        return buf.toString();
    }

    /**
     * Encode a string using Base64 encoding. Used when storing passwords as cookies. This is weak encoding in that
     * anyone can use the decodeString routine to reverse the encoding.
     *
     * @param str String to be encoded
     * @return String encoding result
     */
    public static String encodeString(String str) {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return new String(encoder.encodeBuffer(str.getBytes())).trim();
    }

    /**
     * Decode a string using Base64 encoding.
     *
     * @param str String to be decoded
     * @return String decoding String
     */
    public static String decodeString(String str) {
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        try {
            return new String(dec.decodeBuffer(str));
        } catch (IOException io) {
            throw new RuntimeException(io.getMessage(), io.getCause());
        }
    }

    public static String[] decodeString(String str, String parsing) {
        String orgVal = decodeString(str);
        return orgVal.split(String.format("\\%s", parsing));
    }

    /**
     * convert first letter to a big letter or a small letter.<br>
     *
     * <pre>
     * StringUtil.trim('Password') = 'password'
     * StringUtil.trim('password') = 'Password'
     * </pre>
     *
     * @param str String to be swapped
     * @return String converting result
     */
    public static String swapFirstLetterCase(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.deleteCharAt(0);
        if (Character.isLowerCase(str.substring(0, 1).toCharArray()[0])) {
            sb.insert(0, str.substring(0, 1).toUpperCase());
        } else {
            sb.insert(0, str.substring(0, 1).toLowerCase());
        }
        return sb.toString();
    }

    /**
     * If original String has a specific String, remove specific Strings from original String.
     *
     * <pre>
     * StringUtil.trim('pass*word', '*') = 'password'
     * </pre>
     *
     * @param origString original String
     * @param trimString String to be trimmed
     * @return converting result
     */
    public static String trim(String origString, String trimString) {
        int startPosit = origString.indexOf(trimString);
        if (startPosit != -1) {
            int endPosit = trimString.length() + startPosit;
            return origString.substring(0, startPosit) + origString.substring(endPosit);
        }
        return origString;
    }

    /**
     * Break a string into specific tokens and return a String of last location.
     *
     * <pre>
     * StringUtil.getLastString('password*password*a*b*c', '*') = 'c'
     * </pre>
     *
     * @param origStr  original String
     * @param strToken specific tokens
     * @return String of last location
     */
    public static String getLastString(String origStr, String strToken) {
        StringTokenizer str = new StringTokenizer(origStr, strToken);
        String lastStr = "";
        while (str.hasMoreTokens()) {
            lastStr = str.nextToken();
        }
        return lastStr;
    }

    /**
     * If original String has token, Break a string into specific tokens and change String Array. If not, return a
     * String Array which has original String as it is.
     *
     * <pre>
     * StringUtil.getStringArray('passwordabcpassword', 'abc') 		= String[]{'password','password'}
     * StringUtil.getStringArray('pasword*password', 'abc') 		= String[]{'pasword*password'}
     * </pre>
     *
     * @param str      original String
     * @param strToken specific String token
     * @return String[]
     */
    public static String[] getStringArray(String str, String strToken) {
        if (str.indexOf(strToken) != -1) {
            StringTokenizer st = new StringTokenizer(str, strToken);
            String[] stringArray = new String[st.countTokens()];
            for (int i = 0; st.hasMoreTokens(); i++) {
                stringArray[i] = st.nextToken();
            }
            return stringArray;
        }
        return new String[]{
                str
        };
    }

    /**
     * replace replaced string to specific string from original string. <br>
     *
     * <pre>
     * StringUtil.replace('work$id', '$', '.') 	= 'work.id'
     * </pre>
     *
     * @param str         original String
     * @param replacedStr to be replaced String
     * @param replaceStr  replace String
     * @return converting result
     */
    public static String replace(String str, String replacedStr, String replaceStr) {
        String newStr = "";
        if (str.indexOf(replacedStr) != -1) {
            String s1 = str.substring(0, str.indexOf(replacedStr));
            String s2 = str.substring(str.indexOf(replacedStr) + 1);
            newStr = s1 + replaceStr + s2;
        }
        return newStr;
    }

    /**
     * It converts the string representation of a number to integer type (eg. '27' -> 27)
     *
     * <pre>
     * StringUtil.string2integer('14') 	= 14
     * </pre>
     *
     * @param str     string representation of a number
     * @param pattern pattern String
     * @return boolean which matches the pattern string or not.
     * @throws Exception fail to check pattern matched
     */
    public static boolean isPatternMatching(String str, String pattern) throws Exception {
        // if url has wild key, i.e. "*", convert it to ".*" so that we
        // can
        // perform regex matching
        if (pattern.indexOf('*') >= 0) {
            pattern = pattern.replaceAll("\\*", ".*");
        }

        pattern = "^" + pattern + "$";

        return Pattern.matches(pattern, str);
    }

    /**
     * <p>
     * Checks that the String contains certain characters.
     * </p>
     * <p>
     * A <code>null</code> String will return <code>false</code>. A <code>null</code> invalid character array will
     * return <code>false</code>. An empty String ("") always returns false.
     * </p>
     *
     * <pre>
     * StringUtil.containsInvalidChars(null, *)       			= false
     * StringUtil.containsInvalidChars(*, null)      			= false
     * StringUtil.containsInvalidChars(&quot;&quot;, *)         = false
     * StringUtil.containsInvalidChars(&quot;ab&quot;, '')      = false
     * StringUtil.containsInvalidChars(&quot;abab&quot;, 'xyz') = false
     * StringUtil.containsInvalidChars(&quot;ab1&quot;, 'xyz')  = false
     * StringUtil.containsInvalidChars(&quot;xbz&quot;, 'xyz')  = true
     * </pre>
     *
     * @param str          the String to check, may be null
     * @param invalidChars an array of invalid chars, may be null
     * @return false if it contains none of the invalid chars, or is null
     */

    public static boolean containsInvalidChars(String str, char[] invalidChars) {
        if (str == null || invalidChars == null) {
            return false;
        }
        int strSize = str.length();
        int validSize = invalidChars.length;
        for (int i = 0; i < strSize; i++) {
            char ch = str.charAt(i);
            for (int j = 0; j < validSize; j++) {
                if (invalidChars[j] == ch) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>
     * Checks that the String contains certain characters.
     * </p>
     * <p>
     * A <code>null</code> String will return <code>false</code>. A <code>null</code> invalid character array will
     * return <code>false</code>. An empty String ("") always returns false.
     * </p>
     *
     * <pre>
     * StringUtil.containsInvalidChars(null, *)       			= false
     * StringUtil.containsInvalidChars(*, null)      			= false
     * StringUtil.containsInvalidChars(&quot;&quot;, *)         = false
     * StringUtil.containsInvalidChars(&quot;ab&quot;, '')      = false
     * StringUtil.containsInvalidChars(&quot;abab&quot;, 'xyz') = false
     * StringUtil.containsInvalidChars(&quot;ab1&quot;, 'xyz')  = false
     * StringUtil.containsInvalidChars(&quot;xbz&quot;, 'xyz')  = true
     * </pre>
     *
     * @param str          the String to check, may be null
     * @param invalidChars a String of invalid chars, may be null
     * @return false if it contains none of the invalid chars, or is null
     */
    public static boolean containsInvalidChars(String str, String invalidChars) {
        if (str == null || invalidChars == null) {
            return true;
        }
        return containsInvalidChars(str, invalidChars.toCharArray());
    }

    /**
     * <p>
     * Reverses a String as per {@link StringBuffer#reverse()}.
     * </p>
     * <p>
     * <A code>null</code> String returns <code>null</code>.
     * </p>
     *
     * <pre>
     * StringUtil.reverse(null)  		   = null
     * StringUtil.reverse(&quot;&quot;)    = &quot;&quot;
     * StringUtil.reverse(&quot;bat&quot;) = &quot;tab&quot;
     * </pre>
     *
     * @param str the String to reverse, may be null
     * @return the reversed String, <code>null</code> if null String input
     */

    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * Make a new String that filled original to a special char as cipers
     *
     * @param originalStr original String
     * @param ch          a special char
     * @param cipers      cipers
     * @return filled String
     */
    public static String fillString(String originalStr, char ch, int cipers) {
        int originalStrLength = originalStr.length();

        if (cipers < originalStrLength)
            return null;

        int difference = cipers - originalStrLength;

        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < difference; i++)
            strBuf.append(ch);

        strBuf.append(originalStr);
        return strBuf.toString();
    }

    /**
     * Determine whether a (trimmed) string is empty
     *
     * @param foo The text to check.
     * @return Whether empty.
     */
    public static final boolean isEmptyTrimmed(String foo) {
        return (foo == null || foo.trim().length() == 0);
    }

    /**
     * This method convert "string_util" to "stringUtil"
     *
     * @param targetString
     * @param posChar
     * @return String result
     */
    public static String convertToCamelCase(String targetString, char posChar) {
        StringBuffer result = new StringBuffer();
        boolean nextUpper = false;
        String allLower = targetString.toLowerCase();

        for (int i = 0; i < allLower.length(); i++) {
            char currentChar = allLower.charAt(i);
            if (currentChar == posChar) {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    currentChar = Character.toUpperCase(currentChar);
                    nextUpper = false;
                }
                result.append(currentChar);
            }
        }
        return result.toString();
    }

    /**
     * Convert a string that may contain underscores to camel case.
     *
     * @param underScore Underscore name.
     * @return Camel case representation of the underscore string.
     */
    public static String convertToCamelCase(String underScore) {
        return convertToCamelCase(underScore, '_');
    }

    /**
     * Convert a camel case string to underscore representation.
     *
     * @param camelCase Camel case name.
     * @return Underscore representation of the camel case string.
     */
    public static String convertToUnderScore(String camelCase) {
        String result = "";
        for (int i = 0; i < camelCase.length(); i++) {
            char currentChar = camelCase.charAt(i);
            // This is starting at 1 so the result does not end up with
            // an
            // underscore at the begin of the value
            if (i > 0 && Character.isUpperCase(currentChar)) {
                result = result.concat("_");
            }
            result = result.concat(Character.toString(currentChar).toLowerCase());
        }
        return result;
    }

    /**
     * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의 TIMESTAMP 값을 구하는 기능
     *
     * @return Timestamp 값
     */
    public static String getTimeStamp() {

        String rtnStr = null;

        // 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
        String pattern = "yyyyMMddhhmmssSSS";

        try {
            SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
            Timestamp ts = new Timestamp(System.currentTimeMillis());

            rtnStr = sdfCurrent.format(ts.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rtnStr;
    }

    /**
     * 왼쪽 자리수 채우기
     *
     * @param str
     * @param len
     * @param addStr
     * @return
     */
    public static String lpad(String str, int len, String addStr) {
        String result = str;
        int templen = len - result.length();

        for (int i = 0; i < templen; i++) {
            result = addStr + result;
        }

        return result;
    }

    /**
     * 랜덤 자리수 텍스트
     *
     * @param len
     * @return
     */
    public static String randomText(int len) {
        String result = "";
        for (int i = 0; i < len; i++) {
            // char upperStr = (char)(Math.random() * 26 + 65);
            char lowerStr = (char) (Math.random() * 26 + 97);
            if (i % 2 == 0) {
                result += (int) (Math.random() * 10);
            } else {
                result += lowerStr;
            }
        }
        return result;
    }

    public static boolean isBlank(String str) {
        return (str == null || str.equals(""));
    }

    public static boolean getBoolean(String str, boolean defaultValue) {
        return (StringUtil.isBlank(str) ? defaultValue : (str.equalsIgnoreCase("true") ? true : defaultValue));
    }

    public static String sliceString(String str, int len) {
        return sliceString(str, len, "");
    }

    public static String sliceString(String str, int len, String attachment) {
        StringBuffer temp = new StringBuffer(1024);
        temp.delete(0, temp.length());
        int j, k;

        if (str == null || str.length() < 1) {
            return "";
        }

        if (str.getBytes().length > len + 1) {
            for (j = 0, k = 0; j < len; j++, k++) {
                if (str.substring(j, j + 1).getBytes().length < 2) {
                    temp.append(str.substring(j, j + 1));
                } else {
                    temp.append(str.substring(j, j + 1));
                    k++;
                }

                if (k > len) {
                    break;
                }
            }

            str = temp.append(attachment).toString();
        }

        return str;
    }

    public static String[] split(String strTarget, String[] lens) {
        String[] result = new String[lens.length];
        for (int i = 0, j = 0; i < lens.length; i++, j += Integer.parseInt(lens[i - 1])) {
            result[i] = strTarget.substring(j, j + Integer.parseInt(lens[i]));
        }
        return result;
    }

    public static String[] split(String results, int[] taps) throws UnsupportedEncodingException {
        try {
            String[] ret = new String[taps.length];
            String result;
            result = new String(results.getBytes("KSC5601"), "8859_1");
            int step = 0;
            int c = 0;
            String tmp = "";
            int tap = 0;

            for (int i = 0; i < taps.length; tap = taps[i++]) {
                // for (int tap : taps) {
                try {
                    tmp = result.substring(step, step + tap);
                } catch (Exception e) {
                    tmp = "";
                }
                ret[c++] = tmp;
                step += tap;
            }
            return ret;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static String strip(String sep, String str) {
        String retStr = null;
        if (str != null) {
            StringTokenizer strtoken = new StringTokenizer((String) str, (String) sep);
            if (strtoken.countTokens() > 0) {
                retStr = new String();
                while (strtoken.hasMoreTokens()) {
                    retStr += strtoken.nextToken();
                }
            }
        }
        return retStr;
    }

    public static String replaceString(String org, String var, String tgt, boolean isCaseSensitive) {
        StringBuffer temp = new StringBuffer(1024);
        int end = 0;
        int begin = 0;

        while (true) {
            if (isCaseSensitive) {
                end = org.indexOf(var, begin);
            } else {
                end = org.toLowerCase().indexOf(var, begin);
            }

            if (end == -1) {
                end = org.length();
                temp.append(org.substring(begin, end));
                break;
            }

            temp.append(org.substring(begin, end)).append(tgt);
            begin = end + var.length();
        }

        return temp.toString();
    }

    public static String replaceStringEreg(String org, String pattern, String tgt) {
        Matcher matcher = Pattern.compile(pattern).matcher(org);
        return matcher.replaceAll(tgt);
    }

    public static boolean ereg(String patt, String subj) {
        Matcher matcher = Pattern.compile(patt).matcher(subj);
        return matcher.find();
    }

    public static boolean eregi(String patt, String subj) {
        return ereg(patt, subj.toLowerCase());
    }

    public static String trim(String str) {
        return (str != null) ? str.trim() : str;
    }

    public static String kotoen(String ko) {
        String new_str = null;
        try {
            new_str = new String(ko.getBytes("KSC5601"), "8859_1");
        } catch (java.io.UnsupportedEncodingException e) {
        }
        return new_str;
    }

    public static String entoko(String en) {
        String new_str = null;
        try {
            new_str = new String(en.getBytes("8859_1"), "KSC5601");
        } catch (java.io.UnsupportedEncodingException e) {
        }
        return new_str;
    }

    public static String nlToBr(String org, String var, String tgt) {
        return replaceString(org, var, tgt, false);
    }

    public static String nl2br(String contents) {
        if (contents.indexOf('\n') < 0) {
            return contents;
        }
        StringBuffer buffer = new StringBuffer();
        StringTokenizer st = new StringTokenizer(contents, "\n");
        while (st.hasMoreTokens()) {
            buffer.append(st.nextToken() + "<br>");
        }
        return buffer.toString();
    }

    public static String nl2gt(String contents) {
        if (contents.indexOf('\n') < 0) {
            return "&gt; " + contents + "\n";
        }
        StringBuffer buffer = new StringBuffer();
        StringTokenizer st = new StringTokenizer(contents, "\n");
        while (st.hasMoreTokens()) {
            buffer.append("&gt; " + st.nextToken() + "\n");
        }
        return buffer.toString();
    }

    public static String arrayToString(String[] array) {
        return arrayToString(array, "#");
    }

    public static String arrayToString(Object[] arr, String ptn) {
        String str = "";
        if (arr != null && arr.length > 0) {
            for (int i = 0; i < arr.length; i++) {
                str += (i > 0 ? "," : "") + ptn.replaceAll("#", (String) arr[i]);
            }
        }
        return str;
    }

    public static String stripHTML(String str) {
        String res = str;
        try {
            // res = str.replaceAll("\r", "");
            // res = res.replaceAll("\n", "");
            // res = res.replaceAll("  ", "");
            // res = res.replaceAll("<style(.*)/style>", "");
            res = res.replaceAll("<(?:[^<>]*)>", "");
            // res = res.replaceAll("<(.|\n)*?>", "");
            res = res.replaceAll("&nbsp;", " ");
        } catch (Exception e) {
        }
        return res;
    }

    public static void check_characterset(String name) throws Exception {
        check_characterset(name, null);
    }

    public static void check_characterset(String name, String checkkey) throws Exception {
        String[] chat_set = null;
        /*
         * chat_set = new String[] { "KS_C_5601-1987", "ISO-2022-KR", "EUC-KR",
         * "ISO-2022-JP", "ISO-8859-1", "ISO-8859-2", "ISO-10646-UCS-2",
         * "IBM037", "IBM273", "IBM277", "IBM278", "IBM280", "IBM284", "IBM285",
         * "IBM297", "IBM420", "IBM424", "IBM437", "IBM500", "IBM775", "IBM850",
         * "IBM852", "IBM855", "IBM857", "IBM860", "IBM861", "IBM862", "IBM863",
         * "IBM864", "IBM865", "IBM866", "IBM868", "IBM869", "IBM870", "IBM871",
         * "IBM918", "IBM1026", "Big5-HKSCS", "UNICODE-1-1", "UTF-16BE",
         * "UTF-16LE", "UTF-16", "UTF-8", "ISO-8859-13", "ISO-8859-15", "GBK",
         * "GB18030", "JIS_Encoding", "Shift_JIS", "Big5", "TIS-620",
         * "us-ascii", "iso-8859-1", "iso-8859-2", "iso-8859-3", "iso-8859-4",
         * "iso-8859-5", "iso-8859-6", "iso-8859-7", "iso-8859-8", "iso-8859-9",
         * "koi8-r", "euc-cn", "euc-tw", "big5", "euc-jp", "shift_jis",
         * "iso-2022-jp", "iso-2022-kr" };
         */
        chat_set = new String[]{
                "ASCII", "ISO8859_1", "UnicodeBig", "UnicodeBigUnmarked", "UnicodeLittle", "UnicodeLittleUnmarked", "UTF8", "UTF-16", "EUC_KR", "MS949"
        };
        for (int i = 0; i < chat_set.length; i++) {
            for (int j = 0; j < chat_set.length; j++) {
                if (i != j) {
                    try {
                        // System.out.println("i="+i+":j="+j);
                        String nameTemp = new String(name.getBytes(chat_set[i]), chat_set[j]);
                        // System.out.println(chat_set[i]+":"+chat_set[j]);
                        System.out.println(chat_set[i] + ":" + chat_set[j] + ":" + nameTemp);
                        if (checkkey != null && nameTemp != null && nameTemp.indexOf(checkkey) != -1)
                            System.out.println(chat_set[i] + "," + chat_set[j] + " : " + nameTemp + "<br>");
                    } catch (UnsupportedEncodingException ue) {
                        ue.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @SuppressWarnings({
            "rawtypes", "unchecked"
    })
    public static String[] truncate(String source, int length) throws Exception {
        int type = 1;
        List v = new ArrayList();

        try {
            if (type == 0) {
                char[] buffer = new char[length];
                int byteRead;
                Reader input = new StringReader(new String(source.getBytes(), "UTF-8"));

                while ((byteRead = input.read(buffer, 0, length)) != -1) {
                    String tmp = new String(buffer, 0, byteRead);
                    v.add(new String(tmp.getBytes("UTF-8")));
                }
            } else if (type == 1) {
                String data = source;
                int i, realLen = 0;

                while (data.getBytes().length > length) {
                    i = realLen = 0;

                    for (i = 0; realLen < length; i++) {
                        realLen += ((data.charAt(i) & 0xFF00) == 0) ? 1 : 3;
                    }

                    while ((data.substring(0, i)).getBytes().length > length) {
                        i--;
                    }

                    String tmp = data.substring(0, i);
                    v.add(tmp);
                    data = data.substring(tmp.length());
                }

                if (data.length() > 0) {
                    v.add(data);
                }
            }

            return (String[]) v.toArray(new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static String valueOf(InputStream is, String charset) throws Exception {
        Reader reader = new InputStreamReader(is, charset);
        StringBuffer value = new StringBuffer();
        char[] buffer = new char[1024];
        int byteRead;
        while ((byteRead = reader.read(buffer, 0, 1024)) != -1) {
            ((StringBuffer) value).append(buffer, 0, byteRead);
        }
        return value.toString();
    }

    @SuppressWarnings({
            "rawtypes", "unchecked"
    })
    public static Matcher[] getMatchesPerRow(String source, String pattern) throws Exception {
        Matcher[] mat = null;
        List list = new ArrayList();
        Pattern _ptn = Pattern.compile("(.*)" + pattern + "(.*)", Pattern.CASE_INSENSITIVE);// ([&page|&sn1|&divpage|&sn|&ss|&sc|&select_arrange|&desc][=][a-zA-Z0-9]*)&no=(.*)\"(.*)>([^<>]*)</a>(.*)",
        // Pattern.CASE_INSENSITIVE);
        String[] _result = source.split("\\n");
        String _tmp = "";
        for (int idx = 0; idx < _result.length; idx++) {
            _tmp = _result[idx];
            Matcher m = _ptn.matcher(_tmp);
            if (m.matches()) {
                list.add(_tmp.substring(m.end(1), m.start(m.groupCount())));
            }
        }
        if (list.size() > 0) {
            _ptn = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);// ([&page|&sn1|&divpage|&sn|&ss|&sc|&select_arrange|&desc][=][a-zA-Z0-9]*)&no=(.*)\"(.*)>([^<>]*)</a>(.*)",
            // Pattern.CASE_INSENSITIVE);
            mat = new Matcher[list.size()];
            for (int i = 0; i < list.size(); i++) {
                mat[i] = (Matcher) _ptn.matcher((String) list.get(i));
            }
        }
        return mat;
    }

    public static String subString(String source, String from, String to) {
        return source.substring(source.indexOf(from) + from.length(), source.indexOf(to));
    }

    public static boolean isNumeric(String src) {
        return src.matches("[0-9]*");
    }

    /* from common-lang-1.1 */
    public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0)
            return str;
        else
            return (new StringBuffer(strLen)).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String unicodeDecode(String uni) {
        StringBuffer str = new StringBuffer();
        for (int i = uni.indexOf("\\u"); i > -1; i = uni.indexOf("\\u")) {
            str.append(uni.substring(0, i));
            str.append(String.valueOf((char) Integer.parseInt(uni.substring(i + 2, i + 6), 16)));
            uni = uni.substring(i + 6);
        }
        str.append(uni);
        return str.toString();
    }

    public static String repeat(String arg0, int arg1, String sep) {
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < arg1; i++) {
            if (i != 0)
                ret.append(sep);
            ret.append(arg0);
        }
        return ret.toString();
    }

    /**
     * 배열값에서 일치하는 값있는지 여부
     *
     * @param arg0
     * @param arg1
     * @return
     */
    public static boolean match(String[] arg0, String arg1) {
        for (String str : arg0) {
            if (arg1.equals(str))
                return true;
        }
        return false;
    }

    /**
     * 끝에서 trimStr값 이후로는 잘라버림
     *
     * @param source
     * @param trimStr
     * @return
     */
    public static String lastIndexTrim(String source, String trimStr) {
        if (source != null && source.lastIndexOf(trimStr) != -1) {
            return source.substring(0, source.lastIndexOf(trimStr));
        }
        return source;
    }

    public static int toInt(String data) {
        int result = 0;

        try {
            result = new Integer(data).intValue();
        } catch (Exception e) {
        }

        return result;
    }

    public static long toLong(String data) {
        long result = 0;

        try {
            result = new Long(data).longValue();
        } catch (Exception e) {
        }

        return result;
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append(System.getProperty("line.separator"));
        }

        return sb.toString();
    }

    /**
     * 들어온 문자열을 숫자(Number)로 리턴하는 메쏘드
     */
    public static Number parseNum(String numStr) throws ParseException {
        return NumberFormat.getInstance().parse(numStr);
    }

    /**
     * DB에 넣는 경우 Object 의 값이 null 일 경우 "" 등으로 바꿔여하는 경우가 많으므로 그와 같은 경우에 사용
     */
    public static String replaceNull(String str, String replaceStr) {
        if (str == null) {
            return replaceStr;
        }
        return str;
    }

    public static String convertToStrTrim(String source) {
        String result = null;
        if (source != null) {
            result = source.trim();
        }

        return result;
    }

    /**
     * int 값을 DB에 넣는 경우 Object 의 값이 String 일때 콤마를 없애야하는 경우가 많으므로 그와 같은 경우에 사용
     */
    public static String removeComma(String str) {
        if (str != null) {
            str = StringUtil.replace(str, ",", "");
        }
        return str;
    }
}
