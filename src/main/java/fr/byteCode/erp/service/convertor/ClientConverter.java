package fr.byteCode.erp.service.convertor;

        import fr.byteCode.erp.persistance.dto.ClientDto;
        import fr.byteCode.erp.persistance.entities.Client;


public final class ClientConverter {
    private ClientConverter(){
        super();
    }
    public static Client dtoToModel(ClientDto clientDto){
        return new Client();
    }
    public static ClientDto  ModelToDto(Client client){
        return new ClientDto();
    }
}
