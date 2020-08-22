package me.internalizable.VoiceIdentification.utils;

import com.musicg.fingerprint.FingerprintManager;
import com.musicg.fingerprint.FingerprintSimilarity;
import com.musicg.fingerprint.FingerprintSimilarityComputer;
import com.musicg.wave.Wave;

public class VoiceAnalyzer {


    public float analysisScore() {
        byte[] firstFingerPrint = new FingerprintManager().extractFingerprint(new Wave("E:/Identification/temp.wav"));
        byte[] secondFingerPrint = new FingerprintManager().extractFingerprint(new Wave("E:/Identification/init.wav"));

        FingerprintSimilarity fingerprintSimilarity = new FingerprintSimilarityComputer(firstFingerPrint, secondFingerPrint).getFingerprintsSimilarity();

        return fingerprintSimilarity.getScore();
    }
}
