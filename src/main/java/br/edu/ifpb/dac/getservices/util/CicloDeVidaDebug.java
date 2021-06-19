package br.edu.ifpb.dac.getservices.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class CicloDeVidaDebug implements PhaseListener {
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("Iniciando Fase " + event.getPhaseId());
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}
