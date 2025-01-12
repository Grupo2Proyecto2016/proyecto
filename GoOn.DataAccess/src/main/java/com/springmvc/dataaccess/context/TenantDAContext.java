package com.springmvc.dataaccess.context;

import com.springmvc.dataacces.config.TenantDataSourceConfig;
import com.springmvc.dataaccess.repository.tenant.UserRepository;
import com.springmvc.dataaccess.repository.tenant.ConfiguracionGlobalRepository;
import com.springmvc.dataaccess.repository.tenant.PagoRepository;
import com.springmvc.dataaccess.repository.tenant.VehiculoRepository;
import com.springmvc.dataaccess.repository.tenant.AsientoRepository;
import com.springmvc.dataaccess.repository.tenant.ParadaRepository;
import com.springmvc.dataaccess.repository.tenant.LineaRepository;
import com.springmvc.dataaccess.repository.tenant.RolRepository;
import com.springmvc.dataaccess.repository.tenant.EncomiendaRepository;
import com.springmvc.dataaccess.repository.tenant.ViajeRepository;
import com.springmvc.dataaccess.repository.tenant.TallerRepository;
import com.springmvc.dataaccess.repository.tenant.MantenimientoRepository;
import com.springmvc.dataaccess.repository.tenant.PasajeRepository;
import com.springmvc.dataaccess.repository.tenant.DepartamentoRepository;
import com.springmvc.dataaccess.repository.tenant.CiudadRepository;
import com.springmvc.dataaccess.repository.tenant.SucursalRepository;
import com.springmvc.dataaccess.repository.tenant.ParametroRepository;
import com.springmvc.dataaccess.repository.tenant.DevolucionRepository;
import com.springmvc.dataaccess.repository.tenant.ViajeUbicacionRepository;;

public class TenantDAContext extends TenantDataSourceConfig {

	public UserRepository UserRepository;
	public ConfiguracionGlobalRepository ConfiguracionGlobalRepository;
	public PagoRepository PagoRepository;
	public VehiculoRepository VehiculoRepository;
	public AsientoRepository AsientoRepository;
	public ParadaRepository ParadaRepository; 
	public LineaRepository LineaRepository;
	public RolRepository RolRepository;
	public EncomiendaRepository EncomiendaRepository;
	public ViajeRepository ViajeRepository;
	public TallerRepository TallerRepository;
	public MantenimientoRepository MantenimientoRepository;
	public PasajeRepository PasajeRepository;
	public DepartamentoRepository DepartamentoRepository;
	public CiudadRepository CiudadRepository;
	public SucursalRepository SucursalRepository;
	public ParametroRepository ParametroRepository;
	public DevolucionRepository DevolucionRepository;
	public ViajeUbicacionRepository ViajeUbicacionRepository;
	
	public TenantDAContext(String tenantName, boolean updateSchema)
	{
		super(tenantName, updateSchema);
		UserRepository = new UserRepository(super.EntityManager);
		ConfiguracionGlobalRepository = new ConfiguracionGlobalRepository(super.EntityManager);
		PagoRepository = new PagoRepository(super.EntityManager);
		VehiculoRepository = new VehiculoRepository(super.EntityManager);
		AsientoRepository = new AsientoRepository(super.EntityManager);
		ParadaRepository = new ParadaRepository(super.EntityManager);
		LineaRepository = new LineaRepository(super.EntityManager);
		RolRepository = new RolRepository(super.EntityManager);
		EncomiendaRepository = new EncomiendaRepository(super.EntityManager);
		ViajeRepository = new ViajeRepository(super.EntityManager);
		TallerRepository = new TallerRepository(super.EntityManager);
		MantenimientoRepository = new MantenimientoRepository(super.EntityManager);
		PasajeRepository = new PasajeRepository(super.EntityManager);
		DepartamentoRepository = new DepartamentoRepository(super.EntityManager);
		CiudadRepository = new CiudadRepository(super.EntityManager);
		SucursalRepository = new SucursalRepository(super.EntityManager);
		ParametroRepository = new ParametroRepository(super.EntityManager);
		DevolucionRepository = new DevolucionRepository(super.EntityManager);
		ViajeUbicacionRepository = new ViajeUbicacionRepository(super.EntityManager);
	}
}
