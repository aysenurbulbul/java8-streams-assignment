public class SampleMain{
	public static void main(String[] args){
		PartsStore partsStore = new PartsStore();
		partsStore.FindPartsWithBrand("Hard Drive", "Lenovo");
    	partsStore.FindCheapestMemory(32);
    	partsStore.UpdateStock();
    	partsStore.FindCheapestMemory(16);
    	partsStore.TotalPrice("GPU", "Asus", "GeForce RTX 2080");
    	partsStore.FindFastestCPU();
	}
}
