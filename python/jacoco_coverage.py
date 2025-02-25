import xml.etree.ElementTree as ET

# Path to the JaCoCo XML report
JACOCO_XML_PATH = "dubizzleUtil/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"

def get_coverage_percentage(xml_path):
    tree = ET.parse(xml_path)
    root = tree.getroot()

    covered = 0
    missed = 0

    # Iterate over all <counter> elements in the XML
    for counter in root.findall(".//counter[@type='INSTRUCTION']"):
        covered += int(counter.get("covered", 0))
        missed += int(counter.get("missed", 0))

    total = covered + missed
    coverage_percent = (covered / total) * 100 if total > 0 else 0

    return round(coverage_percent, 2)

if __name__ == "__main__":
    # coverage = get_coverage_percentage(JACOCO_XML_PATH)
    # print(f"📊 Test Coverage: {coverage}%")
    print(get_coverage_percentage(JACOCO_XML_PATH))
    # # Optional: Exit with error if coverage is too low
    # if coverage < 80:
    #     print("❌ Coverage is below 80%! Consider adding more tests.")
    #     exit(1)
