
import java.io.File;

import javax.sound.sampled.AudioFileFormat;

import jAudioFeatureExtractor.jAudioTools.AudioSamples;

public class Test {

	/**
	 * @param args
	 * 
	 */
	Test(){
		// read the file into an AudioSamples object
		
				AudioSamples as = null;
				try {
					as = new AudioSamples(new File("C:/fg.mp3"),"",false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// get the audio from 15 to 30 seconds
				double[][] samples = null;
				try {
					samples = as.getSamplesChannelSegregated(15.0, 30.0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// discard the rest of the samples
				try {
					as.setSamples(samples);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// write the samples to a .wav file
				try {
					as.saveAudio(new File("C:/newAudioFileName.wave"), true, AudioFileFormat.Type.WAVE, false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		System.out.print("l");
		
		
	}


}
