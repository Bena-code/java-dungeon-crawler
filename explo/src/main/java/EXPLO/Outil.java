package src.main.java.EXPLO;

public abstract class Outil extends Objet
{    
    private int conso;
    private String descriptif;

    public int getconsoenergie(){
        return this.conso;
    }
    protected void setConsoenergie(int conso){
        this.conso=conso;
    }
    public String getDescriptif(){
        return this.descriptif;
    }
    protected void setDescriptif(String descriptif){
        this.descriptif = descriptif;
    }

    public String getInfos(){
        return "("+this.getSymbole()+")"+this.getNature()+this.getDescriptif()+" la consomation est de "+this.conso;
    }
    public Outil(String symbole,String nature, String descriptif, int conso)
    {
        super(symbole,nature);
        this.setDescriptif(descriptif);
        this.setConsoenergie(conso);
    }
    public Outil(String symbole,String nature){
        super(symbole,nature);
        
    }

}
