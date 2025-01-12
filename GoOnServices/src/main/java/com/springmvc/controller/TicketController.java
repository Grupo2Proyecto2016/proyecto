	package com.springmvc.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springmvc.entities.tenant.Asiento;
import com.springmvc.entities.tenant.Encomienda;
import com.springmvc.entities.tenant.Linea;
import com.springmvc.entities.tenant.Parada;
import com.springmvc.entities.tenant.Pasaje;
import com.springmvc.entities.tenant.Usuario;
import com.springmvc.entities.tenant.Viaje;
import com.springmvc.entities.tenant.ViajesBuscados;
import com.springmvc.enums.TicketStatus;
import com.springmvc.enums.UserRol;
import com.springmvc.exceptions.CollectTicketException;
import com.springmvc.logic.implementations.LinesLogic;
import com.springmvc.logic.implementations.PackageLogic;
import com.springmvc.logic.implementations.UsersLogic;
import com.springmvc.requestWrappers.AppTicketWrapper;
import com.springmvc.requestWrappers.AvailableSeatsWrapper;
import com.springmvc.requestWrappers.BuyTicketWrapper;
import com.springmvc.requestWrappers.CollectCustomResponseWrapper;
import com.springmvc.requestWrappers.CollectTicketWrapper;
import com.springmvc.requestWrappers.CustomPayPalResponseWrapper;
import com.springmvc.requestWrappers.CustomResponseWrapper;
import com.springmvc.requestWrappers.PayPalWrapper;
import com.springmvc.requestWrappers.TravelSearchWrapper;
import com.springmvc.requestWrappers.seatsFormWrapper;
import com.springmvc.utils.UserContext;

@RestController
@RequestMapping(value = "/{tenantid}")
public class TicketController 
{

	@Autowired
    private UserContext context;
	
	@RequestMapping(value = "/searchTravels", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public ResponseEntity<List<ViajesBuscados>> travels(@RequestBody TravelSearchWrapper searchData, @PathVariable String tenantid)
	{
		
		List<ViajesBuscados> travels = new LinesLogic(tenantid).SearchTravelsAdvanced(searchData.dateFrom, searchData.origins, searchData.destinations); 
		return new ResponseEntity<List<ViajesBuscados>>(travels, HttpStatus.OK);
	}
	
	/*@RequestMapping(value = "/searchTravels", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public ResponseEntity<List<Viaje>> travels(@RequestBody TravelSearchWrapper searchData, @PathVariable String tenantid)
	{
		List<Viaje> travels = new LinesLogic(tenantid).SearchTravels(searchData.dateFrom, searchData.dateTo, searchData.origins, searchData.destinations);
		return new ResponseEntity<List<Viaje>>(travels, HttpStatus.OK);
	}
	 */
	
	@RequestMapping(value = "/getSeats", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public ResponseEntity<List<Asiento>> getSeats(@RequestBody seatsFormWrapper searchData, @PathVariable String tenantid)
	{
		List<Asiento> asientos = new LinesLogic(tenantid).getSeats(searchData.id_viaje, searchData.id_linea, searchData.origen, searchData.destino, searchData.id_vehiculo); 
		return new ResponseEntity<List<Asiento>> (asientos, HttpStatus.OK);		
	}
	
	@Secured({"ROLE_DRIVER"})
	@RequestMapping(value = "/getSeatsDriving", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public ResponseEntity<AvailableSeatsWrapper> getSeatsDriving(@RequestBody seatsFormWrapper searchData, @PathVariable String tenantid)
	{
		int freeSeats = 0;
		AvailableSeatsWrapper response = new AvailableSeatsWrapper();
		response.success = true;
		
		LinesLogic ll = new LinesLogic(tenantid);
		Linea line = ll.GetById(searchData.id_linea);
		response.cost = ll.GetTravelCost(searchData.origen, searchData.destino, searchData.id_linea);
		List<Asiento> asientos = new LinesLogic(tenantid).getSeats(searchData.id_viaje, searchData.id_linea, searchData.origen, searchData.destino, searchData.id_vehiculo); 
		response.seats = asientos;
		
		for (Asiento seat : asientos) 
		{
			if(!seat.getReservado()) 
			{
				freeSeats++;
			}
		}
		if(freeSeats == 0 && !line.getViaja_parado())
		{
			response.success = false;
			response.msg = "No hay m�s asientos disponibles.";
		}
		return new ResponseEntity<AvailableSeatsWrapper> (response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/getTicketValue", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public ResponseEntity<Double> getTicketValue(@RequestBody seatsFormWrapper searchData, @PathVariable String tenantid)
	{
		Double value = new LinesLogic(tenantid).getTicketValue(searchData.origen, searchData.destino, searchData.id_linea);				
		return new ResponseEntity<Double> (value, HttpStatus.OK);		
	}	
	
	@RequestMapping(value = "/getStations", method = RequestMethod.GET)
	public ResponseEntity<List<Parada>> getStations(@PathVariable String tenantid)
	{
		List<Parada> stations = new LinesLogic(tenantid).GetParadas();
		return new ResponseEntity<List<Parada>>(stations, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getFilteredStations", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public ResponseEntity<List<Parada>> getFilteredStations(@RequestBody List<Integer> destinations, @PathVariable String tenantid)
	{
		List<Parada> stations = new LinesLogic(tenantid).GetStationsByDestinations(destinations);
		return new ResponseEntity<List<Parada>>(stations, HttpStatus.OK);
	}		
	
	@Secured({"ROLE_CLIENT"})
	@RequestMapping(value = "/userTickets", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Pasaje>> getUserTickets(@PathVariable String tenantid, HttpServletRequest request) throws Exception
	{
		Usuario currentUser = context.GetUser(request, tenantid);
		LinesLogic ll = new LinesLogic(tenantid);
		List<Pasaje> tickets = ll.GetUserTickets(currentUser);

		return new ResponseEntity<List<Pasaje>>(tickets, HttpStatus.OK);
	}
	
	@Secured({"ROLE_DRIVER"})
	@RequestMapping(value = "/collectTicket", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<CollectCustomResponseWrapper> collectTicket(@RequestBody CollectTicketWrapper collectTicket, @PathVariable String tenantid)
	{
		Pasaje collectedTicket = null;
		CollectCustomResponseWrapper response = new CollectCustomResponseWrapper();
		LinesLogic ll = new LinesLogic(tenantid);
		
		try 
		{
			collectedTicket = ll.CollectTicket(collectTicket.travelId, collectTicket.ticketNumber);
			response.ticket = collectedTicket;
			response.success = true;
			response.msg = "El boleto ha sido cobrado";
		}
		catch (CollectTicketException e)
		{
			response.success = false;
			response.msg = e.getMessage();
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<CollectCustomResponseWrapper>(response, HttpStatus.OK);
	}

	@Secured({"ROLE_SALES", "ROLE_DRIVER"})
	@RequestMapping(value = "/buyTicket", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public ResponseEntity<List<Pasaje>> buyTicket(@RequestBody BuyTicketWrapper buyTicket, @PathVariable String tenantid, HttpServletRequest request)
	{
		Usuario currentUser = context.GetUser(request, tenantid);		
		LinesLogic ll = new LinesLogic(tenantid);
		List<Pasaje> tickets = null;
		
		if(currentUser.getRol() == UserRol.Sales)
		{
			if (buyTicket.rUser != null)
			{
				UsersLogic ul = new UsersLogic(tenantid);
				Usuario comprador = ul.GetUserByName(buyTicket.rUser);
				tickets = ll.SalesBuyTicketsFromUser(comprador, currentUser, buyTicket.id_viaje, buyTicket.origen, buyTicket.destino, buyTicket.valor, buyTicket.seleccionados);
			}
			else if (buyTicket.rDoc != null)
			{
				tickets = ll.SalesBuyTicketsFromCI(buyTicket.rDoc, currentUser, buyTicket.id_viaje, buyTicket.origen, buyTicket.destino, buyTicket.valor, buyTicket.seleccionados);
			}
		}
		else if(currentUser.getRol() == UserRol.Driver)
		{
			tickets = ll.DriverBuyTickets(currentUser, buyTicket.id_viaje, buyTicket.origen, buyTicket.destino, buyTicket.valor, buyTicket.seleccionados);
		}
		
		return new ResponseEntity<List<Pasaje>>(tickets, HttpStatus.OK);
	}
	
	@Secured({"ROLE_SALES", "ROLE_DRIVER"})
	@RequestMapping(value = "/reserveTicket", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public ResponseEntity<List<Pasaje>> reserveTicket(@RequestBody BuyTicketWrapper buyTicket, @PathVariable String tenantid, HttpServletRequest request)
	{
		Usuario currentUser = context.GetUser(request, tenantid);		
		LinesLogic ll = new LinesLogic(tenantid);
		List<Pasaje> tickets = null;
		
		if(currentUser.getRol() == UserRol.Sales)
		{
			if (buyTicket.rUser != null)
			{
				UsersLogic ul = new UsersLogic(tenantid);
				Usuario comprador = ul.GetUserByName(buyTicket.rUser);
				tickets = ll.SalesReserveTicketsFromUser(comprador, currentUser, buyTicket.id_viaje, buyTicket.origen, buyTicket.destino, buyTicket.valor, buyTicket.seleccionados);
			}
			else if (buyTicket.rDoc != null)
			{
				tickets = ll.SalesReserveTicketsFromCI(buyTicket.rDoc, currentUser, buyTicket.id_viaje, buyTicket.origen, buyTicket.destino, buyTicket.valor, buyTicket.seleccionados);
			}
		}
		else if(currentUser.getRol() == UserRol.Driver)
		{
			tickets = ll.DriverBuyTickets(currentUser, buyTicket.id_viaje, buyTicket.origen, buyTicket.destino, buyTicket.valor, buyTicket.seleccionados);
		}
		
		return new ResponseEntity<List<Pasaje>>(tickets, HttpStatus.OK);
	}
	
	@Secured({"ROLE_CLIENT"})
	@RequestMapping(value = "/preBuyTicket", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public ResponseEntity<List<Pasaje>> preBuyTicket(@RequestBody PayPalWrapper paypal, @PathVariable String tenantid, HttpServletRequest request)
	{
		Usuario currentUser = context.GetUser(request, tenantid);		
		LinesLogic ll = new LinesLogic(tenantid);
		List<Pasaje> tickets = null;
		tickets = ll.ClientReserveTickets(currentUser, paypal.id_viaje, paypal.origen, paypal.destino, paypal.valor, paypal.seleccionados);				
		return new ResponseEntity<List<Pasaje>>(tickets, HttpStatus.OK);
	}
	
	@Secured({"ROLE_CLIENT"})
	@RequestMapping(value = "/appConfirmTicket", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public ResponseEntity<Void> appConfirmTicket(@RequestBody AppTicketWrapper atw, @PathVariable String tenantid, HttpServletRequest request)
	{
		LinesLogic ll = new LinesLogic(tenantid);
		List<Pasaje> lst_tickets = new ArrayList<>();
		for(int x=0; x < atw.tickets.size(); x++)
		{
			Pasaje pasaje = ll.findTicketByID(atw.tickets.get(x));
			lst_tickets.add(pasaje);
		}
		ll.ClientConfirmTickets(lst_tickets, atw.id_Pago); 		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@Secured({"ROLE_SALES"})
	@RequestMapping(value = "/getActiveTickets", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Pasaje>> getActiveTickets(@RequestParam Date from, @PathVariable String tenantid)
	{
		from.setHours(0);
		Calendar to = Calendar.getInstance();
		to.setTime(from);
		to.add(GregorianCalendar.DAY_OF_YEAR, 30);
		
		LinesLogic ll = new LinesLogic(tenantid);
		List<Pasaje> tickets = ll.GetActiveTickets(from, to.getTime());
		
		return new ResponseEntity<List<Pasaje>>(tickets, HttpStatus.OK);
	}
	
	@Secured({"ROLE_SALES"})
	@RequestMapping(value = "/cancelTicket", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public ResponseEntity<CustomResponseWrapper> getActiveTickets(@RequestBody Pasaje ticket, @PathVariable String tenantid)
	{
		CustomResponseWrapper response = new CustomResponseWrapper();
		
		LinesLogic ll = new LinesLogic(tenantid);
		ll.CancelTicket(ticket);
		
		if(ticket.getEstado() == TicketStatus.Bought.getValue())
		{
			if(ticket.getPaymentId() != null)
			{
				response.setMsg("El pasaje fue cancelado. El reembolzo ser� acreditado dentro de las pr�ximas 48 horas.");
			}
			else
			{
				response.setMsg(String.format("El pasaje fue cancelado. Debe retornar al cliente $ %s", ticket.getCosto()));
			}
		}
		else
		{
			response.setMsg("El reserva fue cancelada.");
		}
		return new ResponseEntity<CustomResponseWrapper>(response, HttpStatus.OK);
	}
	
	@Secured({"ROLE_SALES"})
	@RequestMapping(value = "/confirmReservation", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public ResponseEntity<Void> confirmReservation(@RequestBody Pasaje ticket, @PathVariable String tenantid)
	{
		LinesLogic ll = new LinesLogic(tenantid);
		ll.SalesConfirmTicket(ticket);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
